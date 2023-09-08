package com.coursetogo.service.course;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.stereotype.Service;

import com.coursetogo.dto.course.RankingItemDTO;

@Service
public class RankingService {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public RankingService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public List<RankingItemDTO> getTopRankingItems() {
        Aggregation aggregation = Aggregation.newAggregation(
        	    Aggregation.group("placeId").count().as("count")
                .first("placeId").as("placeId"),
            Aggregation.sort(Sort.Direction.DESC, "count"),
            Aggregation.limit(5)
        );

        AggregationResults<RankingItemDTO> result = mongoTemplate.aggregate(
                aggregation, "chooseData", RankingItemDTO.class
        );

        return result.getMappedResults();
    }
    public List<String> sortPlaceIdByCount() {
        // placeId로 집계한 결과를 내림차순으로 정렬하는 Aggregation 파이프라인 구성
    	  AggregationOperation sortByCountOperation = Aggregation.sortByCount("placeId");
          AggregationOperation sortOperation = Aggregation.sort(Sort.Direction.DESC, "_id");
        AggregationOperation limitOperation = Aggregation.limit(25);
        Aggregation aggregation = Aggregation.newAggregation(sortByCountOperation, sortOperation, limitOperation);

        // Aggregation 실행하여 정렬된 결과를 가져옴
        AggregationResults<Document> aggregationResults = mongoTemplate.aggregate(aggregation, "chooseData", Document.class);
        List<Document> sortedResults = aggregationResults.getMappedResults();

        // 결과에서 랜덤으로 5개 문서를 선택
        List<Document> randomResults = getRandomDocuments(sortedResults, 5);
        List<String> idList = new ArrayList<>();
        // 선택된 문서 출력
        for (Document document : randomResults) {
        	String id = document.getString("_id");
            idList.add(id);
        }
        return idList;
    }
    public List<String> sortCourseIdByCount() {
        // placeId로 집계한 결과를 내림차순으로 정렬하는 Aggregation 파이프라인 구성
    	  AggregationOperation sortByCountOperation = Aggregation.sortByCount("courseId");
          AggregationOperation sortOperation = Aggregation.sort(Sort.Direction.DESC, "_id");
        AggregationOperation limitOperation = Aggregation.limit(5);
        Aggregation aggregation = Aggregation.newAggregation(sortByCountOperation, sortOperation, limitOperation);

        // Aggregation 실행하여 정렬된 결과를 가져옴
        AggregationResults<Document> aggregationResults = mongoTemplate.aggregate(aggregation, "courseData", Document.class);
        List<Document> sortedResults = aggregationResults.getMappedResults();

        // 결과에서 랜덤으로 5개 문서를 선택
        List<Document> randomResults = getRandomDocuments(sortedResults, 5);
        List<String> idList = new ArrayList<>();
        // 선택된 문서 출력
        for (Document document : randomResults) {
        	String id = document.getString("_id");
            idList.add(id);
        }
//        System.out.println("idList =" + idList);
        return idList;
    }
    private List<Document> getRandomDocuments(List<Document> documents, int count) {
    	   List<Document> randomDocuments = new ArrayList<>(documents); // 문서 리스트를 복사하여 수정 가능한 리스트 생성

    	    // 랜덤으로 문서 선택
    	    Collections.shuffle(randomDocuments);
    	    randomDocuments = randomDocuments.subList(0, Math.min(count, randomDocuments.size())); // count 값 이하로 잘라냄

    	    return randomDocuments;
    }
    
}