package ni.com.userApi.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Token {
  private String token;

  public Token(String token) {
    this.token = token;
  }
}
