package com.konektedi.vs.utilities.Db;
//
//import android.arch.persistence.room.Database;
//import android.arch.persistence.room.Room;
//import android.arch.persistence.room.RoomDatabase;
//import android.content.Context;
//
//import com.konektedi.konektedi.Responses.CommentsEntity;
//import com.konektedi.konektedi.Responses.CommentsDao;
//
///**
// * Created by Sy on 2/24/2018.
// */
//
//@Database(entities = {CommentsEntity.class, FeedsEntity.class}, version = 2)
//public abstract class AppDatabase extends RoomDatabase {
//
//    private static AppDatabase INSTANCE;
//
//    public static AppDatabase getDatabase(final Context context) {
//        if (INSTANCE == null) {
//            synchronized (AppDatabase.class) {
//                if (INSTANCE == null) {
//                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
//                            AppDatabase.class, "KonektediDB")
//                            .fallbackToDestructiveMigration()
//                            .build();
//                }
//            }
//        }
//        return INSTANCE;
//    }
//
//    public static void destroyInstance() {
//        INSTANCE = null;
//    }
//
//    public abstract CommentsDao commentsDao();
//
//    public abstract FeedsDao feedsDao();
//
//}
