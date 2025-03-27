class Destination {
  final int id;
  final String name;
  final String location;
  final String description;
  final String imageUrl;

  Destination({
    required this.id,
    required this.name,
    required this.location,
    required this.description,
    required this.imageUrl,
  });

  factory Destination.fromJson(Map<String, dynamic> json) {
    return Destination(
      id: json['id'],
      name: json['name'],
      location: json['location'],
      description: json['description'],
      imageUrl: json['imageUrl'],
    );
  }
}
