package com.endava.dao;

import com.endava.model.Document;
import org.bson.types.ObjectId;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class DocumentDAO {

	private String name;
	public static final Date DATE123 = new Date();
	private String fileName;

	public Date getDate() {
		return DATE123;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	// GET ALL
	public List<Document> readAll(MongoCollection mongoCollection) {

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

	//GET ONE
	public Document findOne(MongoCollection mongoCollection, String id) {
		return  mongoCollection.findOne(new ObjectId(id)).as(Document.class);
	}
}
