import 'package:flutter/material.dart';

import '../../models/destination.dart';
import '../../services/api_service.dart';

class DestinationListWidget extends StatefulWidget {
  @override
  _DestinationListWidgetState createState() => _DestinationListWidgetState();
}

class _DestinationListWidgetState extends State<DestinationListWidget> {
  late Future<List<Destination>> futureDestinations;

  @override
  void initState() {
    super.initState();
    futureDestinations = ApiService().fetchDestinations();
  }

  @override
  Widget build(BuildContext context) {
    return FutureBuilder<List<Destination>>(
      future: futureDestinations,
      builder: (context, snapshot) {
        if (snapshot.connectionState == ConnectionState.waiting) {
          return Center(child: CircularProgressIndicator());
        } else if (snapshot.hasError) {
          return Center(child: Text("Error: ${snapshot.error}"));
        } else if (!snapshot.hasData || snapshot.data!.isEmpty) {
          return Center(child: Text("No destinations found"));
        } else {
          return SizedBox(
            height: 200,
            child: ListView(
              scrollDirection: Axis.horizontal,
              padding: EdgeInsets.all(16),
              children: snapshot.data!.map((destination) {
                return DestinationCard(destination: destination);
              }).toList(),
            ),
          );
        }
      },
    );
  }
}

class DestinationCard extends StatelessWidget {
  final Destination destination;

  DestinationCard({required this.destination});

  @override
  Widget build(BuildContext context) {
    return Container(
      width: 150,
      margin: EdgeInsets.only(right: 10),
      decoration: BoxDecoration(
        borderRadius: BorderRadius.circular(10),
        image: DecorationImage(
          image: NetworkImage(destination.imageUrl),
          fit: BoxFit.cover,
        ),
      ),
      child: Stack(
        children: [
          Positioned(
            bottom: 10,
            left: 10,
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Text(destination.name, style: TextStyle(color: Colors.white, fontWeight: FontWeight.bold, fontSize: 16)),
                Row(
                  children: [
                    Icon(Icons.star, color: Colors.yellow, size: 16),
                    Text("4.5", style: TextStyle(color: Colors.white)),
                  ],
                ),
              ],
            ),
          ),
          Positioned(
            top: 10,
            right: 10,
            child: Icon(Icons.favorite, color: Colors.pinkAccent),
          ),
        ],
      ),
    );
  }
}
