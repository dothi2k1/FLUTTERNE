import 'package:flutter/material.dart';
import 'destination_list_widget.dart';
import 'article_list_widget.dart';

class HomePage extends StatelessWidget {
  const HomePage({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.white,
      appBar: AppBar(
        backgroundColor: Colors.lightBlue,
        elevation: 0,
        title: Text(
          "Hi Guy!",
          style: TextStyle(color: Colors.white, fontSize: 22, fontWeight: FontWeight.bold),
        ),
      ),
      body: SingleChildScrollView(
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [

            Padding(
              padding: EdgeInsets.all(16.0),
              child: Text("Where are you going next?", style: TextStyle(fontSize: 18)),
            ),
            // Ô tìm kiếm
            Padding(
              padding: EdgeInsets.symmetric(horizontal: 50),
              child: TextField(
                decoration: InputDecoration(
                  prefixIcon: Icon(Icons.search),
                  hintText: "Search your destination",
                  border: OutlineInputBorder(borderRadius: BorderRadius.circular(10)),
                  filled: true,
                  fillColor: Colors.white,
                ),
              ),
            ),
            SizedBox(height: 10),

            // Thanh danh mục icon
            Padding(
              padding: EdgeInsets.symmetric(horizontal: 16),
              child: Center(
                child: Wrap(

                  alignment: WrapAlignment.center,
                  spacing: 50,
                  children: [
                    CategoryIcon(icon: Icons.hotel, label: "Hotels", color: Colors.orange),
                    CategoryIcon(icon: Icons.flight, label: "Flights", color: Colors.pink),
                    CategoryIcon(icon: Icons.apps, label: "All", color: Colors.green),
                  ],
                ),
              ),
            ),


            SizedBox(height: 20),
            Padding(
              padding: EdgeInsets.symmetric(horizontal: 16),
              child: Text("Popular Destinations", style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold, color: Colors.black87)),
            ),
            DestinationListWidget(),
            ArticleListWidget(),
          ],
        ),
      )
    );
  }
}

// Widget cho icon danh mục
class CategoryIcon extends StatelessWidget {
  final IconData icon;
  final String label;
  final Color color;

  const CategoryIcon({required this.icon, required this.label, required this.color, super.key});

  @override
  Widget build(BuildContext context) {
    return Column(
      children: [
        Container(
          padding: EdgeInsets.symmetric(horizontal: 30, vertical: 15),
          decoration: BoxDecoration(
            color: color.withOpacity(0.2),
            borderRadius: BorderRadius.circular(10),
          ),
          child: Icon(icon, color: color, size: 28),
        ),
        SizedBox(height: 5),
        Text(label, style: TextStyle(fontSize: 14, fontWeight: FontWeight.w500)),
      ],
    );
  }
}
