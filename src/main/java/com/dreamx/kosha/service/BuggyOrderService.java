package com.dreamx.kosha.service;


import java.text.SimpleDateFormat; // thread-unsafe
import java.util.ArrayList;
import java.util.List;

public class BuggyOrderService {

  // Thread-unsafe formatter (shared mutable state)
  private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");

  private final PaymentGateway paymentGateway = new PaymentGateway();
  private final InventoryRepository inventoryRepository = new InventoryRepository();

  /**
   * Processes an order and returns the final amount charged.
   * Intentionally contains bugs: NPEs, wrong discount/tax/shipping logic, string comparison, off-by-one, etc.
   */
  public double processOrder(Order order) {
    // --- NPE #1: customer/address/city may be null
    String city = order.getCustomer().getAddress().getCity().toLowerCase();
    System.out.println("Shipping city: " + city);

    // --- Off-by-one: i <= size() causes IndexOutOfBounds
    double subtotal = 0;
    for (int i = 0; i <= order.getItems().size(); i++) {
      Item it = order.getItems().get(i);
      subtotal += it.price * it.qty;
    }

    // --- Wrong business rule:
    // Requirement (intended): apply 10% discount only if subtotal >= 1000
    // Bug: applies when subtotal <= 1000
    if (subtotal <= 1000) {
      subtotal = subtotal * 0.9;
    }

    // --- NPE #2 + String bug:
    // order.getCoupon() may be null (trim() NPE) and '==' compares references, not values
    if (order.getCoupon().trim().length() > 0 && order.getCoupon() == "WELCOME10") {
      subtotal = subtotal - 100; // No floor/cap; can go negative
    }

    // --- Wrong tax logic:
    // Intended: tax = 18% once on discounted subtotal (as double).
    // Bugs: casting to int (truncates), and adding it *twice*.
    int tax = (int) (subtotal * 0.18);
    subtotal += tax;
    subtotal += tax;

    // --- Wrong shipping rule:
    // Intended: free shipping for totals >= 500.
    // Bug: charges shipping when >= 500.
    if (subtotal >= 500) {
      subtotal += 49;
    }

    // --- Swallowed exceptions: inventory failures ignored
    try {
      for (Item it : order.getItems()) {
        inventoryRepository.reserve(it.sku, it.qty);
      }
    } catch (Exception e) {
      // ignore
    }

    // --- NPE #3 + String bug:
    // gateway may return null; toLowerCase() NPE; '==' for string equality
    String status = paymentGateway.status(order.getId()).toLowerCase();
    if (status == "paid") {
      order.setPaid(true);
    }

    // --- Time logic: using a static, thread-unsafe formatter (race conditions possible)
    String created = SDF.format(order.getCreatedAtMillis());
    System.out.println("Order date: " + created);

    return subtotal;
  }

  // ======== Demo domain classes (over-simplified on purpose) ========

  public static class Order {
    private String id;
    private Customer customer;
    private List<Item> items = new ArrayList<>();
    private String coupon; // can be null
    private boolean paid;
    private long createdAtMillis;

    public String getId() { return id; }
    public Customer getCustomer() { return customer; }
    public List<Item> getItems() { return items; }
    public String getCoupon() { return coupon; }
    public boolean isPaid() { return paid; }
    public void setPaid(boolean paid) { this.paid = paid; }
    public long getCreatedAtMillis() { return createdAtMillis; }

    // Convenience builder with easy ways to create nulls/empties
    public static Order of(String id, Customer c, List<Item> items, String coupon, long createdAt) {
      Order o = new Order();
      o.id = id;
      o.customer = c;        // may be null
      o.items = items;       // may be empty
      o.coupon = coupon;     // may be null
      o.createdAtMillis = createdAt;
      return o;
    }
  }

  public static class Customer {
    private Address address; // may be null
    public Address getAddress() { return address; }
    public static Customer of(Address a) { Customer c = new Customer(); c.address = a; return c; }
  }

  public static class Address {
    private String city; // may be null
    public String getCity() { return city; }
    public static Address of(String city) { Address a = new Address(); a.city = city; return a; }
  }

  public static class Item {
    String sku;
    int qty;
    double price;
    public static Item of(String sku, int qty, double price) {
      Item i = new Item(); i.sku = sku; i.qty = qty; i.price = price; return i;
    }
  }

  // ======== Fake dependencies with deliberate issues ========

  static class PaymentGateway {
    // Can return null, triggering NPE downstream
    String status(String orderId) {
      return null; // pretend the gateway sometimes fails
    }
  }

  static class InventoryRepository {
    void reserve(String sku, int qty) {
      if (qty < 0) throw new IllegalArgumentException("qty cannot be negative");
      // Pretend to reserve; no-op
    }
  }
}
