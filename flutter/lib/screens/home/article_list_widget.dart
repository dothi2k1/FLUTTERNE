import 'package:flutter/material.dart';

import '../../models/article.dart';

class ArticleListWidget extends StatelessWidget {
  // Dữ liệu JSON mô phỏng danh sách bài viết
  final List<Map<String, dynamic>> articlesJson = [
    {
      "id": 1,
      "title": "Khám phá Hội An",
      "description": "Hội An là một thành phố cổ nổi tiếng với kiến trúc truyền thống và ẩm thực phong phú.",
      "imageUrl": "https://images2.thanhnien.vn/528068263637045248/2025/3/24/nha-trang-17428130131821343548929.jpg"
    },
    {
      "id": 2,
      "title": "Sài Gòn về đêm",
      "description": "Khám phá Sài Gòn với những con phố nhộn nhịp và các quán cà phê đẹp.",
      "imageUrl": "https://images2.thanhnien.vn/528068263637045248/2025/3/24/nha-trang-17428130131821343548929.jpg"
    },
    {
      "id": 3,
      "title": "Hà Nội - Thủ đô nghìn năm văn hiến",
      "description": "Hà Nội với Hồ Gươm, Phố Cổ và nền ẩm thực phong phú.",
      "imageUrl": "https://images2.thanhnien.vn/528068263637045248/2025/3/24/nha-trang-17428130131821343548929.jpg"
    },
    {
      "id": 4,
      "title": "Sài Gòn về đêm",
      "description": "Khám phá Sài Gòn với những con phố nhộn nhịp và các quán cà phê đẹp.",
      "imageUrl": "https://images2.thanhnien.vn/528068263637045248/2025/3/24/nha-trang-17428130131821343548929.jpg"
    },
    {
      "id": 5,
      "title": "Hà Nội - Thủ đô nghìn năm văn hiến",
      "description": "Hà Nội với Hồ Gươm, Phố Cổ và nền ẩm thực phong phú.",
      "imageUrl": "https://images2.thanhnien.vn/528068263637045248/2025/3/24/nha-trang-17428130131821343548929.jpg"
    }
  ];

  @override
  Widget build(BuildContext context) {
    List<Article> articles = articlesJson.map((json) => Article.fromJson(json)).toList();

    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Padding(
          padding: EdgeInsets.symmetric(horizontal: 16, vertical: 10),
          child: Text("Bài viết nổi bật", style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold, color: Colors.black87)),
        ),
        ListView.builder(
          shrinkWrap: true,
          physics: NeverScrollableScrollPhysics(), // Để tránh conflict với ListView chính
          itemCount: articles.length,
          itemBuilder: (context, index) {
            return ArticleCard(article: articles[index]);
          },
        ),
      ],
    );
  }
}

class ArticleCard extends StatelessWidget {
  final Article article;

  ArticleCard({required this.article});

  @override
  Widget build(BuildContext context) {
    return Card(
      margin: EdgeInsets.symmetric(horizontal: 16, vertical: 8),
      child: Row(
        children: [
          Container(
            width: 100,
            height: 100,
            decoration: BoxDecoration(
              borderRadius: BorderRadius.circular(8),
              image: DecorationImage(
                image: NetworkImage(article.imageUrl),
                fit: BoxFit.cover,
              ),
            ),
          ),
          SizedBox(width: 10),
          Expanded(
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Text(article.title, style: TextStyle(fontSize: 16, fontWeight: FontWeight.bold)),
                SizedBox(height: 5),
                Text(article.description, maxLines: 2, overflow: TextOverflow.ellipsis),
              ],
            ),
          ),
        ],
      ),
    );
  }
}
