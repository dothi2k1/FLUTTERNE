import 'package:flutter/material.dart';
import '../models/order.dart';

class OrderTable extends StatelessWidget {
  final List<Order> orders;
  final Function(String) onDelete; // Callback xoá đơn hàng

  const OrderTable({super.key, required this.orders, required this.onDelete});

  @override
  Widget build(BuildContext context) {
    return SingleChildScrollView(
      scrollDirection: Axis.horizontal,
      child: DataTable(
        columns: const [
          DataColumn(label: Text('Xoá')),
          DataColumn(label: Text('Mã SP')),
          DataColumn(label: Text('Tên sản phẩm')),
          DataColumn(label: Text('Giá')),
          DataColumn(label: Text('Tiền tệ')),
          DataColumn(label: Text('Số lượng')),
        ],
        rows: orders.map((order) {
          return DataRow(cells: [
            DataCell(
              IconButton(
                icon: const Icon(Icons.delete, color: Colors.red),
                onPressed: () => onDelete(order.item), // Gọi hàm xoá
              ),
            ),
            DataCell(Text(order.item)),
            DataCell(Text(order.itemName)),
            DataCell(Text("\$${order.price.toStringAsFixed(2)}")),
            DataCell(Text(order.currency)),
            DataCell(Text(order.quantity.toString())),
          ]);
        }).toList(),
      ),
    );
  }
}
