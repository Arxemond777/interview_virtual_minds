package main.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import main.example.dto.VideoDTO;
import main.example.dto.VideoWrapperDTO;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class Driver {
    static class Criteria {
        public static List<VideoDTO> filterVideosByActiveDates(List<VideoDTO> videos, LocalDate startDate, LocalDate endDate) {
            List<VideoDTO> filteredVideos = new ArrayList<>();
            for (VideoDTO video : videos) {
                LocalDate videoStartDate = video.getActiveDates().getStart();
                LocalDate videoEndDate = video.getActiveDates().getEnd();
                if (videoStartDate.compareTo(startDate) >= 0 && videoEndDate.compareTo(endDate) <= 0) {
                    filteredVideos.add(video);
                }
            }
            return filteredVideos;
        }
    }
    public static void main(String[] args) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
//        File jsonFile = new File("/resources/video_advertisements.json");
        ClassLoader classLoader = Driver.class.getClassLoader(); // Get the class loader
        try {
            VideoWrapperDTO videoWrapperDTO = mapper.readValue(classLoader.getResourceAsStream("video_advertisements.json"), VideoWrapperDTO.class);

//            System.out.println(calculateTotalDurationByMonth(videoWrapperDTO.getVideos()));
//            calculateAverageDuration(videoWrapperDTO.getVideos());
//            System.out.println(Criteria.filterVideosByActiveDates(videoWrapperDTO.getVideos(), LocalDate.of(2023, 3, 10), LocalDate.of(2023, 11, 9)));
            System.out.println();

            System.out.println(groupAndAggregate(videoWrapperDTO.getVideos()));

//            System.out.println(videoWrapperDTO.getVideos());
//            System.out.println(videoWrapperDTO.getVideos().stream().map(s -> s.getVideoId()).max(Long::compareTo));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void calculateAverageDuration(List<VideoDTO> videos) {
        for (VideoDTO video : videos) {
            long durationSeconds = video.getDurationSeconds();
            long activeDays = video.getActiveDates().getEnd().toEpochDay() - video.getActiveDates().getStart().toEpochDay();
            double averageDuration = durationSeconds / (double) activeDays;
            System.out.println("Video ID: " + video.getVideoId() + ", Average Active Duration: " + averageDuration + " seconds per day");
        }
    }

    public static Map<String, Long> calculateTotalDurationByMonth(List<VideoDTO> videos) {
        Map<String, Long> metrics = new HashMap<>();
        for (VideoDTO video : videos) {
            String month = video.getActiveDates().getStart().toString().split("-")[1];
            Long duration = video.getDurationSeconds();
            metrics.put(month, metrics.getOrDefault(month, 0L) + duration);
        }
        return metrics;
    }

    public static Map<String, Double> groupAndAggregate(List<VideoDTO> videos) {
        Map<String, Double> aggregatedData = new HashMap<>();
        Map<String, List<VideoDTO>> groupedData = new HashMap<>();

        // Group videos by month
        for (VideoDTO video : videos) {
            String month = video.getActiveDates().getStart().toString().split("-")[1];
            groupedData.computeIfAbsent(month, k -> new ArrayList<>()).add(video);
        }

        // Aggregate data for each month
        for (String month : groupedData.keySet()) {
            List<VideoDTO> monthVideos = groupedData.get(month);
            double totalSellPrice = monthVideos.stream().mapToDouble(VideoDTO::getSellPrice).sum();
            double avgSellPrice = totalSellPrice / monthVideos.size();
            aggregatedData.put(month, avgSellPrice);
        }

        return aggregatedData;
    }

//    public List<VideoDTO> findVideosByCriteria(List<VideoDTO> videos, Criteria criteria) {
//        List<VideoDTO> result = new ArrayList<>();
//        for (VideoDTO video : videos) {
//            if (criteria.test(video)) {
//                result.add(video);
//            }
//        }
//        return result;
//    }
}
