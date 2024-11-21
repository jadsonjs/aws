package example;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Hello World example of AWS lambda
 */
public class Hello implements RequestHandler<Map<String, Object>, Map<String, String>> {

    /**
     *
     * In AWS Lambda, a handleRequest method is the entry point of your Lambda function.
     * It processes incoming events and generates responses.
     *
     * Create a New Lambda Function
     *    Click on Create function.
     *    Specify the handler is the entry point to your function
     *
     *    AWS Lambda expects the handler to follow this format:
     *    package.ClassName::methodName
     *
     * POST method
     * @param input
     * @param context
     * @return
     */
    @Override
    public Map<String, String> handleRequest(Map<String, Object> input, Context context) {

        String body = (String) input.get("body");
        System.out.println("body:"+body);

        ObjectMapper objectMapper = new ObjectMapper();

        String fileName = UUID.randomUUID()+".json" ;
        System.out.println("trying to sabe the file: "+fileName);

        try {

            // write an object (a json file) to S3
            String jsonData = objectMapper.writeValueAsString("{ \"id\" : 1 }");

            S3Client s3 = S3Client.builder().build();
            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket("my-first-aws-bucket-21101982")
                    .key(fileName) // object name
                    .build();

            System.out.println("putObject: "+jsonData);

            s3.putObject(request, RequestBody.fromString(jsonData));

            // read an object form S3

            S3Client s3Client = S3Client.builder().build();
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket("my-first-aws-bucket-21101982")
                    .key(fileName) // object name
                    .build();

            InputStream isFile = s3Client.getObject(getObjectRequest);

            String jsonDataReturned = objectMapper.readValue(isFile, String.class);
            System.out.println(jsonDataReturned);

        } catch (Exception e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }

        Map<String, String> response = new HashMap<>();
        response.put("file", fileName);
        return response;

    }
}
