package service;

import model.Category;
import model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductServiceImpl implements PService {

    public static final String SEARCH="{call searchProduct(?)}";
    public static final String JOIN_PRODUCT_CATEGORY = "select * from product join category c on product.id_cate = c.cate_id;";

    private Connection connection = connectDB.getConnect();

    public Category findCateById(int id) {
        Category category = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from category where cate_id=?;");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
//                int ID= resultSet.getInt("cate_id");
                String name = resultSet.getString("cate_name");
                category = new Category(id, name);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return category;
    }

    public List<Product> searchProduct(String name) {
        List<Product> list = new ArrayList<>();
        try {
            CallableStatement callableStatement= connection.prepareCall(SEARCH);
            callableStatement.setString(1, "%" + name + "%");
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nameproduct = resultSet.getString("nameproduct");
                double price = resultSet.getDouble("price");
                int quantity = resultSet.getInt("quantity");
                String color = resultSet.getString("color");
                int categoryId = resultSet.getInt("cate_id");
                String categoryName = resultSet.getString("cate_name");
                Category category1 = new Category(categoryId, categoryName);
                list.add(new Product(id, nameproduct, price, quantity, color, category1));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;
    }

    public Category findCateByName(String name) {
        Category category = null;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("select * from category where cate_name=?;");
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int ID = resultSet.getInt("cate_id");
                String name1 = resultSet.getString("cate_name");
                category = new Category(ID, name1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return category;
    }

    @Override
    public List<Product> selectAllProduct() {
        List<Product> list = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(JOIN_PRODUCT_CATEGORY);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("nameproduct");
                double price = resultSet.getDouble("price");
                int quantity = resultSet.getInt("quantity");
                String color = resultSet.getString("color");
                int categoryId = resultSet.getInt("cate_id");
                String categoryName = resultSet.getString("cate_name");
                Category category1 = new Category(categoryId, categoryName);
                list.add(new Product(id, name, price, quantity, color, category1));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;
    }

    public Product findProById(int id) {
        Product product = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from product where id=?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("nameproduct");
                double price = resultSet.getDouble("price");
                int quantity = resultSet.getInt("quantity");
                String color = resultSet.getString("color");
                int category = resultSet.getInt("category");
                Category category1 = findCateById(category);
                product = new Product(id, name, price, quantity, color, category1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return product;
    }

    @Override
    public void insertProduct(Product product) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into product values (?,?,?,?,?,?);");
            preparedStatement.setInt(1, product.getId());
            preparedStatement.setString(2, product.getName());
            preparedStatement.setDouble(3, product.getPrice());
            preparedStatement.setInt(4, product.getQuantity());
            preparedStatement.setString(5, product.getColor());
            preparedStatement.setInt(6, product.getCategory().getCateId());
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public List<Category> selectAllCate() {
        List<Category> list = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from category;");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                list.add(new Category(id, name));

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;
    }

    @Override
    public Product selectProduct(int id) {
        Product product = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from product join category c on product.id_cate = c.cate_id where id=?;");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("nameproduct");
                double price = resultSet.getDouble("price");
                int quantity = resultSet.getInt("quantity");
                String color = resultSet.getString("color");
                int categoryId = resultSet.getInt("cate_id");
                String categoryName = resultSet.getString("cate_name");
                Category category1 = new Category(categoryId, categoryName);
                product = new Product(id, name, price, quantity, color, category1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return product;

    }

    @Override
    public void deleteProduct(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("delete from product where id=?;");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void updateProduct(Product product) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("update product set nameproduct=?, price=?, quantity=?,color=?, id_cate=? where id=?");
            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.setInt(3, product.getQuantity());
            preparedStatement.setString(4, product.getColor());
            preparedStatement.setInt(5, product.getCategory().getCateId());
            preparedStatement.setInt(6, product.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
