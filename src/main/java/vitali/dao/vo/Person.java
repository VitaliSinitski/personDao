package vitali.dao.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vitali.dao.annotation.MyColumn;
import vitali.dao.annotation.MyTable;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder


@MyTable(name = "Person")
public class Person {

    @MyColumn(name = "id")
    private int id;
    @MyColumn(name = "name")
    private String name;
    @MyColumn(name = "surname")
    private String surname;


}
