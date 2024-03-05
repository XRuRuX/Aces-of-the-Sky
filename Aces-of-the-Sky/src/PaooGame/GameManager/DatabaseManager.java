package PaooGame.GameManager;

import PaooGame.GameObjects.Player;

import java.sql.*;

public class DatabaseManager {
    private float MAXSPEED;
    private int shotTime;
    private double bulletSize;
    private float bulletSpeed;
    public void connect() {
        Connection connection = null;
        try {
            String url = "res/database.db";
            connection = DriverManager.getConnection("jdbc:sqlite:" + url);

            String query = "SELECT MAXSPEED, shotTime, bulletSize, bulletSpeed FROM GameSettings";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                shotTime = resultSet.getInt("shotTime");
                MAXSPEED = Float.parseFloat(resultSet.getString("MAXSPEED"));
                bulletSize = Double.parseDouble(resultSet.getString("bulletSize"));
                bulletSpeed = Float.parseFloat(resultSet.getString("bulletSpeed"));
            }
            resultSet.close();
            statement.close();
            connection.close();

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(float MAXSPEED, int shotTime, double bulletSize, float bulletSpeed) {
        Connection connection = null;
        this.MAXSPEED = MAXSPEED;
        this.shotTime = shotTime;
        this.bulletSize = bulletSize;
        this.bulletSpeed = bulletSpeed;
        try {
            String url = "res/database.db";
            connection = DriverManager.getConnection("jdbc:sqlite:" + url);
            PreparedStatement statement = connection.prepareStatement("UPDATE GameSettings SET MAXSPEED = ?, shotTime = ?, bulletSize = ?, bulletSpeed = ?");
            statement.setString(1, Float.toString(MAXSPEED));
            statement.setInt(2, shotTime);
            statement.setString(3, Double.toString(bulletSize));
            statement.setString(4, Float.toString(bulletSpeed));
            statement.executeUpdate();
            statement.close();
            connection.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public float getMAXSPEED() { return MAXSPEED; }
    public int getShotTime() { return shotTime; }
    public double getBulletSize() { return bulletSize; }
    public float getBulletSpeed() { return bulletSpeed; }
}