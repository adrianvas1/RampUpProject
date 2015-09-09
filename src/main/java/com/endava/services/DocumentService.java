package com.endava.services;

import com.endava.dao.DocumentDAO;
import com.endava.model.Document;
import com.mongodb.DB;
import org.bson.types.ObjectId;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class DocumentService {

    @Autowired
    private Jongo jongo;

    @Autowired
    private DB db;

    @Autowired
    private MongoCollection mongoCollection;

    @Autowired
    private DocumentDAO documentDAO;

    @Autowired
    private Document document;

    private OutputStream os;

    // READ - Get All
    public List<Document> readAll() {
        MongoCursor<Document> cursor = mongoCollection.find().as(Document.class);
        List<Document> collection = new ArrayList<Document>();
        while (cursor.hasNext()) {
            collection.add(cursor.next());
        }
        try {
            cursor.close();
        } catch (IOException e) {

        }
        return collection;
    }

    // READ - Get one
    public Document read(String id) {
        document = mongoCollection.findOne(new ObjectId(id)).as(Document.class);
        return document;
    }

    // CREATE - POST
    public String create(String name) {
        document.setName(name);
        mongoCollection.save(document);
        return document.get_id();
    }

    // UPDATE - PATCH
    public void update(String id, String name) {
        mongoCollection.update(new ObjectId(id)).with("{$set: {'name': '" + name + "'}}");
    }

    // DELETE - DELETE
	public void delete(String id) {
        mongoCollection.remove(new ObjectId(id));
	}
	// UPLOAD FILE
	public boolean writeToFile(String uploadedFileLocation, String upDocument, String fileName)
			throws IOException {
        if (upDocument.matches("(.*)name(.*)")) {

            JSONObject jsonObj = new JSONObject(upDocument);
            document.setName((String) jsonObj.get("name"));
            document.setFileName(fileName);

            JSONObject outputStream = new JSONObject(document);
            FileWriter file = new FileWriter(uploadedFileLocation);
            file.write(String.valueOf(outputStream));
            file.flush();
            file.close();
            return true;
        }
        else {
            return false;
        }
	}
}
