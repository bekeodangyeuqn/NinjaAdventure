package firebase.util;

import java.util.ArrayList;
import java.util.List;

public class test {



	public static void main(String[] args) {
		  // Tạo một danh sách các chuỗi
        List<String> list = new ArrayList<>();
        list.add("abc");
        list.add("def");
        list.add("ghi");

        // In danh sách trước khi xóa
        System.out.println("Danh sách trước khi xóa: " + list);

        // Gọi phương thức remove để xóa chuỗi "abc"
        list.remove("abc");

        // In danh sách sau khi xóa
        System.out.println("Danh sách sau khi xóa: " + list);
	}

}
