import 'package:flutter/material.dart';
import '../models/order.dart';
import '../services/json_service.dart';
import '../widgets/order_table.dart';
import '../widgets/order_form.dart';
import '../widgets/search_bar.dart';

class OrderScreen extends StatefulWidget {
  const OrderScreen({super.key});

  @override
  _OrderScreenState createState() => _OrderScreenState();
}

class _OrderScreenState extends State<OrderScreen> {
  final JsonService jsonService = JsonService();
  List<Order> orders = [];

  @override
  void initState() {
    super.initState();
    _initializeData();
  }

  Future<void> _initializeData() async {
    await jsonService.initializeOrders(); // Tạo đơn hàng mẫu
    _loadOrders();
  }

  void _loadOrders() async {
    final loadedOrders = await jsonService.readOrders();
    setState(() {
      orders = loadedOrders;
    });
  }

  void _addOrder(Order order) async {
    await jsonService.addOrder(order);
    _loadOrders();
  }

  void _deleteOrder(String itemId) async {
    await jsonService.deleteOrder(itemId);
    _loadOrders();
  }


  void _searchOrders(String query) async {
    final searchedOrders = await jsonService.searchOrders(query);
    setState(() {
      orders = searchedOrders;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Column(
        children: [
          Expanded(
            child: SingleChildScrollView(
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.stretch,
                children: [
                  Padding(
                    padding: const EdgeInsets.all(8.0),
                    child: Card(
                      elevation: 4,
                      shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(10)),
                      child: Padding(
                        padding: const EdgeInsets.all(16.0),
                        child: OrderForm(onAddOrder: _addOrder),
                      ),
                    ),
                  ),

                  Padding(
                    padding: const EdgeInsets.symmetric(horizontal: 8.0),
                    child: SearchBarWidget(onSearch: _searchOrders),
                  ),

                  Padding(
                    padding: const EdgeInsets.all(8.0),
                    child: Card(
                      elevation: 4,
                      shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(10)),
                      child: Padding(
                        padding: const EdgeInsets.all(8.0),
                        child: OrderTable(
                          orders: orders,
                          onDelete: _deleteOrder, // Truyền hàm xoá
                        ),
                      ),
                    ),
                  ),
                ],
              ),
            ),
          ),
        ],
      ),
    );
  }
}
