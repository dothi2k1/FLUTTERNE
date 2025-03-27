import 'dart:convert';
import 'package:shared_preferences/shared_preferences.dart';
import '../models/order.dart';

class JsonService {
  static const String _orderKey = 'orders';

  /// Khởi tạo đơn hàng mẫu nếu chưa có dữ liệu
  Future<void> initializeOrders() async {
    final prefs = await SharedPreferences.getInstance();
    final String? jsonString = prefs.getString(_orderKey);
      List<Order> sampleOrders = [
        Order(item: "A1000", itemName: "iPhone 15", price: 1200, currency: "USD", quantity: 1),
        Order(item: "A1001", itemName: "iPhone 16", price: 1500, currency: "USD", quantity: 2),
        Order(item: "A1002", itemName: "Samsung Galaxy S23", price: 1100, currency: "USD", quantity: 1),
      ];
      await _saveOrders(sampleOrders);
  }

  /// Đọc danh sách đơn hàng từ SharedPreferences
  Future<List<Order>> readOrders() async {
    final prefs = await SharedPreferences.getInstance();
    final String? jsonString = prefs.getString(_orderKey);

    if (jsonString != null) {
      List<dynamic> jsonList = json.decode(jsonString);
      return jsonList.map((item) => Order.fromJson(item)).toList();
    } else {
      return [];
    }
  }

  /// Thêm đơn hàng mới vào danh sách
  Future<void> addOrder(Order order) async {
    List<Order> orders = await readOrders();
    orders.add(order);
    await _saveOrders(orders);
  }

  /// Xoá đơn hàng theo mã sản phẩm (Item ID)
  Future<void> deleteOrder(String itemId) async {
    List<Order> orders = await readOrders();
    orders.removeWhere((order) => order.item == itemId);
    await _saveOrders(orders);
  }

  /// Tìm kiếm đơn hàng theo tên sản phẩm (ItemName)
  Future<List<Order>> searchOrders(String query) async {
    List<Order> orders = await readOrders();
    return orders
        .where((order) => order.itemName.toLowerCase().contains(query.toLowerCase()))
        .toList();
  }

  /// Lưu danh sách đơn hàng vào SharedPreferences
  Future<void> _saveOrders(List<Order> orders) async {
    final prefs = await SharedPreferences.getInstance();
    String jsonString = json.encode(orders.map((e) => e.toJson()).toList());
    await prefs.setString(_orderKey, jsonString);
  }
}
