import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainApp {

    public static class Builder {
        private String subject;
        private Map<String, String> claims;

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public Map<String, String> getClaims() {
            return claims;
        }

        public void setClaims(Map<String, String> claims) {
            this.claims = claims;
        }

        public void compact() {
            try (PrintWriter out = new PrintWriter("1.txt")) {
                out.println(subject);
                claims.entrySet().stream().forEach(c -> out.println(c.getKey() + "\t" + c.getValue()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static class Claims {
        private String subject;
        private Map<String, String> claims;

        public Claims() {
            claims = new  HashMap<>();
            try {
                List<String> data = Files.readAllLines(Paths.get("1.txt"));
                subject = data.get(0);
                for (int i = 1; i < data.size(); i++) {
                    claims.put(data.get(i).split("\t")[0], data.get(i).split("\t")[1]);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String get(String key) {
            return claims.get(key);
        }

    }

    public static void main(String[] args) {


        Builder builder = new Builder();
        builder.setSubject("bob");
        Map<String, String> claims = new HashMap<>();
        claims.put("email", "bob@gmail.com");
        claims.put("role", "user");
        builder.setClaims(claims);
        builder.compact();



        Claims claimsFromToken = new Claims();
        System.out.println(claimsFromToken.getSubject());
        System.out.println(claimsFromToken.get("role"));
        System.out.println(claimsFromToken.get("email"));
    }
}
