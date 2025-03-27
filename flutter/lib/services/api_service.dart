import 'dart:convert';
import 'package:http/http.dart' as http;

import '../models/destination.dart';

class ApiService {
  static const String baseUrl = "http://localhost:8090/api/destinations"; // Localhost cho Android Emulator

  Future<List<Destination>> fetchDestinations() async {
    final response = await http.get(Uri.parse(baseUrl));

    if (response.statusCode == 200) {
      List<dynamic> data = json.decode(response.body);
      return data.map((json) => Destination.fromJson(json)).toList();
    } else {
      throw Exception('Failed to load destinations');
    }
  }
}
