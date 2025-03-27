import 'package:dothiflutterexam/screens/order_screen.dart';
import 'package:flutter/material.dart';

class SearchPage extends StatelessWidget {
  const SearchPage({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      // appBar: AppBar(title: const Text("Search Page")),
      body: const Center(
        child: const OrderScreen(),
      ),
    );
  }
}
