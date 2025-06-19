package org.nutrition.app.meal.service;

import lombok.NonNull;
import org.nutrition.app.exception.NutritionError;
import org.nutrition.app.exception.NutritionException;
import org.nutrition.app.meal.constants.MealConstants;
import org.nutrition.app.meal.entity.DetectedObject;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
public class InferenceClient {

    private final RestTemplate restTemplate;

    public InferenceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<DetectedObject> predict(final MultipartFile image) {
        try {
            Resource imageResource = new ByteArrayResource(image.getBytes()) {
                @Override
                @NonNull
                public String getFilename() {
                    String orig = image.getOriginalFilename();
                    return (orig != null && !orig.isBlank()) ? orig : "";
                }
            };

            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("file", imageResource);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

            ResponseEntity<DetectedObject[]> response = restTemplate.postForEntity(
                    MealConstants.INFERENCE_SERVICE_URL,
                    requestEntity,
                    DetectedObject[].class
            );

            DetectedObject[] detectedObjects = response.getBody();
            return Arrays.asList(detectedObjects);

        } catch (IOException e) {
            throw new NutritionException( NutritionError.FAILED_TO_READ_IMAGE_BYTES, HttpStatus.INTERNAL_SERVER_ERROR, e);
        }catch (Exception e) {
            throw new NutritionException(NutritionError.ERROR_COMUNICATING_WITH_INFERENCE_SERVICE, HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }
}