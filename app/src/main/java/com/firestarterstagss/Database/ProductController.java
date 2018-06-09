package com.firestarterstagss.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.firestarterstagss.Models.UserProfileModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Windows on 2/13/2018.
 */

public class ProductController {
    Context ctx;
    String TAG = "Add Product Error";

    public ProductController(Context ctx) {
        this.ctx = ctx;
    }

 //   user_id varchar, insID varchar, username varchar, cookie varchar, csrf varchar, profilepic varchar, IMEI varchar

    public boolean addUsre(UserProfileModel UserProfileModel) {
        Cursor cursor;
        boolean b = false;
        DBHelper helper = new DBHelper(ctx, DBHelper.DataBaseName, null, DBHelper.Version);
        SQLiteDatabase database = helper.getWritableDatabase();

        try {
            String query = "SELECT * FROM " + DBHelper.TableName + " WHERE insID = ?";
            cursor = database.rawQuery(query, new String[]{String.valueOf(UserProfileModel.getPk())});
            if (cursor.getCount() == 0) {
                ContentValues values = new ContentValues();
                values.put("user_id", UserProfileModel.getUserID());
                values.put("insID", UserProfileModel.getPk());
                values.put("username", UserProfileModel.getUsername());
                values.put("cookie", UserProfileModel.getCookie());
                values.put("csrf", UserProfileModel.getCsrf_token());
                values.put("profilepic", UserProfileModel.getProfilePicUrl());
                values.put("Edge_array", UserProfileModel.getEdge_array());
                values.put("IMEI", UserProfileModel.getIMEI());

                database.insert(DBHelper.TableName, null, values);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            Log.e(TAG, e + "");
        }
        return false;
    }

    public List<UserProfileModel> getAllProduct() {
        List<UserProfileModel> plist = new ArrayList<>();
        Cursor cursor;
        DBHelper helper = new DBHelper(ctx, DBHelper.DataBaseName, null, DBHelper.Version);
        SQLiteDatabase database = helper.getWritableDatabase();
        Cursor cr = database.rawQuery("select * from " + DBHelper.TableName, null);

        while (cr.moveToNext() == true) {
            UserProfileModel dm = new UserProfileModel();
            dm.setUserID(cr.getString(1));
            dm.setPk(Long.valueOf(cr.getString(2)));
            dm.setUsername(cr.getString(3));
            dm.setCookie(cr.getString(4));
            dm.setCsrf_token(cr.getString(5));
            dm.setProfilePicUrl(cr.getString(6));
            dm.setIMEI(cr.getString(7));
            dm.setEdge_array(cr.getString(8));
            plist.add(dm);
        }
        return plist;
    }
//
//    public UserProfileModel getSingleProduct(String product_id) {
//
//        DBHelper helper = new DBHelper(ctx, DBHelper.DataBaseName, null, DBHelper.Version);
//        SQLiteDatabase database = helper.getWritableDatabase();
//        String query = "SELECT * FROM " + DBHelper.TableName + " WHERE product_id = ?";
//        Cursor cr = database.rawQuery(query, new String[]{product_id});
//        while (cr.moveToNext() == true) {
//            UserProfileModel dm = new UserProfileModel();
//            dm.setId(cr.getString(0));
//            dm.setProduct_id(cr.getString(1));
//            dm.setTitle(cr.getString(2));
//            dm.setCategory(cr.getString(3));
//            dm.setSub_category(cr.getString(4));
//            dm.setDescription(cr.getString(5));
//            dm.setSale_price(cr.getString(6));
//            dm.setDiscount_price(cr.getString(7));
//            dm.setImage(cr.getString(8));
//            dm.setSelected_quantity(cr.getString(9));
//            dm.setAdd_note(cr.getString(10));
//            dm.setIs_created(cr.getString(11));
//            return dm;
//        }
//        return null;
//    }
//
//    public boolean deleteProduct(String product_id) {
//        Cursor cursor;
//        DBHelper helper = new DBHelper(ctx, DBHelper.DataBaseName, null, DBHelper.Version);
//        SQLiteDatabase database = helper.getWritableDatabase();
//
//        try {
//            String query = "SELECT * FROM " + DBHelper.TableName + " WHERE product_id = ?";
//            cursor = database.rawQuery(query, new String[]{product_id});
//            if (cursor.getCount() != 0) {
//                database.delete(DBHelper.TableName, "product_id = ?", new String[]{product_id});
//
//                String query2 = "SELECT * FROM " + DBHelper.TableName + " WHERE product_id = ?";
//                cursor = database.rawQuery(query2, new String[]{product_id});
//                if (cursor.getCount() == 0) {
//                    return true;
//                } else {
//                    return false;
//                }
//            }
//        } catch (Exception e) {
//            Log.e("Error delete", e + "");
//        }
//        return false;
//    }
//
//    public boolean updateProduct(UserProfileModel UserProfileModel) {
//
//        Cursor cursor;
//        DBHelper helper = new DBHelper(ctx, DBHelper.DataBaseName, null, DBHelper.Version);
//        SQLiteDatabase database = helper.getWritableDatabase();
//        try {
//            String query = "SELECT * FROM " + DBHelper.TableName + " WHERE product_id = ?";
//            cursor = database.rawQuery(query, new String[]{UserProfileModel.getProduct_id()});
//            if (cursor.getCount() != 0) {
//                ContentValues values = new ContentValues();
//                values.put("selected_quantity", UserProfileModel.getSelected_quantity());
//
//                Log.e("select", UserProfileModel.getSelected_quantity());
//
//                database.update(DBHelper.TableName, values, "product_id = ?", new String[]{UserProfileModel.getProduct_id()});
//
//                return true;
//            } else {
//                return false;
//            }
//        } catch (Exception e) {
//            Log.e("Error delete", e + "");
//        }
//        return false;
//    }
//
//    public boolean updateNote(UserProfileModel UserProfileModel) {
//
//        Cursor cursor;
//        DBHelper helper = new DBHelper(ctx, DBHelper.DataBaseName, null, DBHelper.Version);
//        SQLiteDatabase database = helper.getWritableDatabase();
//        try {
//            String query = "SELECT * FROM " + DBHelper.TableName + " WHERE product_id = ?";
//            cursor = database.rawQuery(query, new String[]{UserProfileModel.getProduct_id()});
//            if (cursor.getCount() != 0) {
//                ContentValues values = new ContentValues();
//                values.put("add_note", UserProfileModel.getAdd_note());
//
//                Log.e("select", UserProfileModel.getSelected_quantity());
//
//                database.update(DBHelper.TableName, values, "product_id = ?", new String[]{UserProfileModel.getProduct_id()});
//
//                return true;
//            } else {
//                return false;
//            }
//        } catch (Exception e) {
//            Log.e("Error delete", e + "");
//        }
//        return false;
//    }
//
//    public int getItemsCount() {
//        Cursor cursor;
//        boolean b = false;
//        DBHelper helper = new DBHelper(ctx, DBHelper.DataBaseName, null, DBHelper.Version);
//        SQLiteDatabase database = helper.getReadableDatabase();
//
//        try {
//            String query = "SELECT * FROM " + DBHelper.TableName;
//            cursor = database.rawQuery(query, null);
//            if (cursor.getCount() != 0) {
//                int count = cursor.getCount();
//                cursor.close();
//                return count;
//            }
//        } catch (Exception e) {
//            Log.e("get count eroo", e + "");
//        }
//        return 0;
//    }
//
    public boolean clearData() {
        DBHelper helper = new DBHelper(ctx, DBHelper.DataBaseName, null, DBHelper.Version);
        SQLiteDatabase database = helper.getWritableDatabase();
        try {
            database.execSQL("delete from " + DBHelper.TableName);
            return true;
        } catch (Exception e) {
            Log.e("Error clear", e + "");
        }
        return false;
    }

}
