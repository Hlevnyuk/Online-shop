package com.luxoft.dao.jdbc.mapper;
import com.luxoft.entity.Product;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
public class ProductsRowMapper {
    public static Product mapRow(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        double price = resultSet.getDouble("price");
        String description = resultSet.getString("description");
        Date publishDate = resultSet.getDate("date");
        return Product.builder().
                id(id)
                .name(name)
                .price(price)
                .description(description)
                .publishDate(publishDate.toLocalDate())
                .build();
    }
}
