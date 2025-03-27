package vn.emiu.picabe.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@Configuration
@EnableCaching
public class RedisConfig {

    // Cấu hình RedisTemplate để sử dụng với các đối tượng serializable
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        // Cấu hình serializables
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());

        return template;
    }

    // Cấu hình CacheManager với Redis
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        // Cấu hình Redis Cache Writer, với chế độ non-locking để đảm bảo không bị lỗi khi Redis không sẵn sàng
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory);

        // Cấu hình thời gian hết hạn cache (ví dụ 10 phút)
        RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(1))  // Đặt TTL cho các cache (10 phút)
                .disableCachingNullValues();  // Không cache các giá trị null

        // Trả về Redis Cache Manager
        return RedisCacheManager.builder(redisCacheWriter)
                .cacheDefaults(defaultCacheConfig)
                .build();
    }

    // Kiểm tra kết nối Redis có sẵn hay không
    @Bean
    public boolean isRedisAvailable(RedisConnectionFactory connectionFactory) {
        try {
            // Kiểm tra kết nối Redis bằng cách thực hiện một "PING"
            connectionFactory.getConnection().ping();
            return true; // Redis có sẵn
        } catch (Exception e) {
            return false; // Redis không có sẵn
        }
    }
}
