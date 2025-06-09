package vn.group10.moviebookingapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "moviebookingapp.db";
    private static final int DB_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Bảng người dùng
        db.execSQL("CREATE TABLE User (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "email TEXT UNIQUE," +
                "password TEXT," +
                "phone TEXT," +
                "role TEXT)");

        // Bảng phim
        db.execSQL("CREATE TABLE Movie (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "title TEXT," +
                "description TEXT," +
                "poster TEXT," +
                "duration INTEGER," +
                "genre TEXT)");

        // Bảng rạp
        db.execSQL("CREATE TABLE Theater (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "location TEXT)");

        // Lịch chiếu
        db.execSQL("CREATE TABLE Showtime (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "movie_id INTEGER," +
                "theater_id INTEGER," +
                "datetime TEXT," +
                "FOREIGN KEY(movie_id) REFERENCES Movie(id)," +
                "FOREIGN KEY(theater_id) REFERENCES Theater(id))");

        // Ghế
        db.execSQL("CREATE TABLE Seat (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "showtime_id INTEGER," +
                "seat_number TEXT," +
                "is_booked INTEGER," +
                "FOREIGN KEY(showtime_id) REFERENCES Showtime(id))");

        // Đồ ăn nước
        db.execSQL("CREATE TABLE FoodDrink (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "price REAL," +
                "image TEXT)");

        // Voucher
        db.execSQL("CREATE TABLE Voucher (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "code TEXT UNIQUE," +
                "discount REAL," +
                "expiry_date TEXT)");

        // Booking
        db.execSQL("CREATE TABLE Booking (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "user_id INTEGER," +
                "showtime_id INTEGER," +
                "seat_info TEXT," +          // VD: "A1,A2"
                "food_info TEXT," +          // VD: "1x Popcorn, 2x Coke"
                "total_price REAL," +
                "voucher_id INTEGER," +
                "final_price REAL," +
                "email_sent INTEGER," +
                "FOREIGN KEY(user_id) REFERENCES User(id)," +
                "FOREIGN KEY(showtime_id) REFERENCES Showtime(id)," +
                "FOREIGN KEY(voucher_id) REFERENCES Voucher(id))");

        Log.d("DBHelper", "Database created");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Xoá và tạo lại nếu nâng version
        db.execSQL("DROP TABLE IF EXISTS Booking");
        db.execSQL("DROP TABLE IF EXISTS Voucher");
        db.execSQL("DROP TABLE IF EXISTS FoodDrink");
        db.execSQL("DROP TABLE IF EXISTS Seat");
        db.execSQL("DROP TABLE IF EXISTS Showtime");
        db.execSQL("DROP TABLE IF EXISTS Theater");
        db.execSQL("DROP TABLE IF EXISTS Movie");
        db.execSQL("DROP TABLE IF EXISTS User");

        onCreate(db);
    }
}

