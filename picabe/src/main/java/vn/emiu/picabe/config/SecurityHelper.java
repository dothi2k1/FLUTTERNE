package vn.emiu.picabe.config;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import vn.emiu.picabe.entity.User;
import vn.emiu.picabe.exception.NotFoundException;
import vn.emiu.picabe.exception.UnauthorizedException;
import vn.emiu.picabe.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class SecurityHelper {

    private final UserRepository userRepository;

    public static Long getCurrentAccountId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof Jwt)) {
            throw new UnauthorizedException("Vui lòng tạo tài khoản/đăng nhập để sử dụng tính năng này.");
        }

        Jwt jwt = (Jwt) authentication.getPrincipal();
        Object accountIdObj = jwt.getClaims().get("accountId");

        if (accountIdObj instanceof Number) {
            return ((Number) accountIdObj).longValue();
        } else {
            throw new UnauthorizedException("User không hợp lệ: " + accountIdObj);
        }
    }

    public User getCurrentAccount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof Jwt)) {
            throw new UnauthorizedException("Vui lòng tạo tài khoản/đăng nhập để sử dụng tính năng này.");
        }

        Jwt jwt = (Jwt) authentication.getPrincipal();
        Object accountIdObj = jwt.getClaims().get("accountId");

        if (!(accountIdObj instanceof Number)) {
            throw new UnauthorizedException("accountId không hợp lệ trong JWT: " + accountIdObj);
        }

        Long accountId = ((Number) accountIdObj).longValue();
        System.out.println("Account ID: " + accountId);

        return userRepository.findById(accountId)
                .orElseThrow(() -> new NotFoundException("Account not found with ID: " + accountId));
    }

}
