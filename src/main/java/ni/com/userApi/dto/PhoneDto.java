package ni.com.userApi.dto;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PhoneDto implements Serializable {
  private String number;
  private Integer citycode;
  private Integer contrycode;
}
