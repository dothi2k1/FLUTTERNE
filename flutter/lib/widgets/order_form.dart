import 'package:flutter/material.dart';
import '../models/order.dart';

class OrderForm extends StatefulWidget {
  final Function(Order) onAddOrder;

  const OrderForm({super.key, required this.onAddOrder});

  @override
  _OrderFormState createState() => _OrderFormState();
}

class _OrderFormState extends State<OrderForm> {
  final _formKey = GlobalKey<FormState>();
  final _itemController = TextEditingController();
  final _itemNameController = TextEditingController();
  final _priceController = TextEditingController();
  final _currencyController = TextEditingController(text: "USD");
  final _quantityController = TextEditingController();

  void _submitOrder() {
    if (_formKey.currentState!.validate()) {
      final newOrder = Order(
        item: _itemController.text,
        itemName: _itemNameController.text,
        price: double.parse(_priceController.text),
        currency: _currencyController.text,
        quantity: int.parse(_quantityController.text),
      );
      widget.onAddOrder(newOrder);

      // Reset form sau khi thêm đơn hàng
      _formKey.currentState!.reset();
      _currencyController.text = "USD"; // Giữ lại giá trị USD mặc định
    }
  }

  @override
  Widget build(BuildContext context) {
    return  Padding(
          padding: const EdgeInsets.all(5.0),
          child: Form(
            key: _formKey,
            child: Column(
              mainAxisSize: MainAxisSize.min,
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Center(
                  child: Text(
                    "Thêm đơn hàng",
                    style: Theme.of(context).textTheme.titleLarge!.copyWith(
                      color: Colors.lightBlue, // Màu xanh
                      fontWeight: FontWeight.w200, // Đậm hơn cho nổi bật
                    ),
                  ),
                ),
                const SizedBox(height: 16),

                // 2 ô Input trên cùng một hàng
                Row(
                  children: [
                    Expanded(
                      child: TextFormField(
                        controller: _itemController,
                        decoration: const InputDecoration(labelText: "Item Code"),
                        validator: (value) => value!.isEmpty ? "Nhập mã sản phẩm" : null,
                      ),
                    ),
                    const SizedBox(width: 10),
                    Expanded(
                      child: TextFormField(
                        controller: _itemNameController,
                        decoration: const InputDecoration(labelText: "Item Name"),
                        validator: (value) => value!.isEmpty ? "Nhập tên sản phẩm" : null,
                      ),
                    ),
                  ],
                ),
                const SizedBox(height: 10),

                // 2 ô tiếp theo trên cùng một hàng
                Row(
                  children: [
                    Expanded(
                      child: TextFormField(
                        controller: _priceController,
                        keyboardType: TextInputType.number,
                        decoration: const InputDecoration(labelText: "Price"),
                        validator: (value) => value!.isEmpty ? "Nhập giá" : null,
                      ),
                    ),
                    const SizedBox(width: 10),
                    Expanded(
                      child: TextFormField(
                        controller: _currencyController,
                        decoration: const InputDecoration(labelText: "Currency"),
                        readOnly: true,
                      ),
                    ),
                  ],
                ),
                const SizedBox(height: 10),

                // Quantity
                TextFormField(
                  controller: _quantityController,
                  keyboardType: TextInputType.number,
                  decoration: const InputDecoration(labelText: "Quantity"),
                  validator: (value) => value!.isEmpty ? "Nhập số lượng" : null,
                ),

                const SizedBox(height: 20),

                // Button Thêm đơn hàng
                Center(
                  child: ElevatedButton.icon(
                    onPressed: _submitOrder,
                    style: ElevatedButton.styleFrom(
                      backgroundColor: Colors.blue,
                      padding: const EdgeInsets.symmetric(horizontal: 40, vertical: 12),
                      shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(8)),
                    ),
                    icon: const Icon(Icons.add_shopping_cart, color: Colors.white), // Icon thêm đơn hàng
                    label: const Text("Thêm đơn hàng", style: TextStyle(fontSize: 16, color: Colors.white)),
                  ),
                ),

              ],
            ),
          ),
    );
  }
}
