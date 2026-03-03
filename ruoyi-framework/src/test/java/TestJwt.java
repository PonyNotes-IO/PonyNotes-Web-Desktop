import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.sign.Base64;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.FixedClock;
import org.apache.logging.log4j.util.Base64Util;
import org.springframework.cglib.beans.BeanMap;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;

public class TestJwt {

    public static void main(String[] args) throws UnsupportedEncodingException, ParseException {

        String jwt =
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJjNTkwMDJmNi00M2EzLTQ1MWEtYWVkNi0zYjY2MGE2MWNhMTQiLCJhdWQiOiIiLCJleHAiOjE3NzA3ODA2NDUsImlhdCI6MTc3MDc3MzQ0NSwiZW1haWwiOiI4NzEwMzk3OEBxcS5jb20iLCJwaG9uZSI6IiIsImFwcF9tZXRhZGF0YSI6eyJwcm92aWRlciI6ImVtYWlsIiwicHJvdmlkZXJzIjpbImVtYWlsIl19LCJ1c2VyX21ldGFkYXRhIjp7ImVtYWlsIjoiODcxMDM5NzhAcXEuY29tIiwiZW1haWxfdmVyaWZpZWQiOnRydWUsInBob25lX3ZlcmlmaWVkIjpmYWxzZSwic3ViIjoiYzU5MDAyZjYtNDNhMy00NTFhLWFlZDYtM2I2NjBhNjFjYTE0In0sInJvbGUiOiIiLCJhYWwiOiJhYWwxIiwiYW1yIjpbeyJtZXRob2QiOiJwYXNzd29yZCIsInRpbWVzdGFtcCI6MTc3MDc3MzQ0NX1dLCJzZXNzaW9uX2lkIjoiZTQ1ODEwOWUtNzhlOS00YmFhLWJkYWQtMDFiYzkyM2RjZmJiIiwiaXNfYW5vbnltb3VzIjpmYWxzZX0.NagwT5MygQXl-FSfoo5zReelPUd0G1OzSFI72LO2IO8"
                ;

        String encode = Base64.encode("hello456".getBytes("utf-8"));
        System.out.println(encode);
        Claims claims = (Claims) Jwts.parser()
                .setClock(new FixedClock(DateUtils.parseDate("2026-02-09","yyyy-MM-dd")))
                .setSigningKey(encode)
                        .parse(jwt).getBody();
        System.out.println(BeanMap.create(claims));
    }
}
