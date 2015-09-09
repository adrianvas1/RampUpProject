package com.endava.util;



import com.endava.model.Document;
import com.endava.model.DocumentTest;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;
import org.json.JSONObject;

import java.io.*;
import java.nio.charset.Charset;

public class MongoTest {

    @SuppressWarnings("resource")
    public static void main(String[] args) throws IOException {
        /*DB db = new MongoClient().getDB("mydb");

        Jongo jongo = new Jongo(db);
        MongoCollection coll = jongo.getCollection("test2");

        Document doc2 = new Document();
        doc2.setName("test");

        coll.insert(doc2);
        System.out.println("I have inserted: " + doc2.get_id());

        MongoCursor<Document> all = coll.find().as(Document.class);
        while (all.hasNext()) {
            //  System.out.println(all.next());
        }*/
        serializeAddress("docoment1");
    }

    public static void serializeAddress(String name) {

        DocumentTest document = new DocumentTest();

        try {

          /*  FileOutputStream fout = new FileOutputStream("d:\\uploadedtest.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(document);
            oos.close();
            System.out.println("Done");*/

            document.setName(name);
            JSONObject jsonObj = new JSONObject(document);
            FileWriter file = new FileWriter("d:\\uploadedtest.txt");
            file.write(String.valueOf(jsonObj));
            file.flush();
            file.close();


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

