package gmailapi;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.ListMessagesResponse;
import com.google.api.services.gmail.model.Message;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.services.gmail.model.MessagePart;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GmailQuickstart {
    private static final String APPLICATION_NAME = "Gmail API Java Quickstart";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";
    public static int messagesCount;

    // Scopes for Gmail API
    private static final List<String> SCOPES = Collections.singletonList("https://www.googleapis.com/auth/gmail.readonly");
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        InputStream in = GmailQuickstart.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();

        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

    public static int getMessagesCount()
    {
        return getMessages().size();
    }

    private static List<Message> getMessages()
    {
        try {
            final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            Gmail service = new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                    .setApplicationName(APPLICATION_NAME)
                    .build();

            String query = "newer_than:1d";
            String user = "me";
            ListMessagesResponse listResponse = service.users().messages().list(user).setQ(query).execute();
            List<Message> messages = listResponse.getMessages();
            return messages;
        }
        catch (IOException | GeneralSecurityException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getConfirmationLink()
    {
        try{
            final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            Gmail service = new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                    .setApplicationName(APPLICATION_NAME)
                    .build();
            String user = "me";
            String messageId = getMessages().get(0).getId();
            Message message = service.users().messages().get(user, messageId).execute();

            for (MessagePart part : message.getPayload().getParts()) {
                if (part.getMimeType().equals("text/html")) {
                    String data = part.getBody().getData();
                    String decodedData = new String(java.util.Base64.getUrlDecoder().decode(data));

                    String confirmationLink = extractConfirmationLink(decodedData);
                    return confirmationLink;
                }
            }
            return null;
        }
        catch (IOException | GeneralSecurityException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String extractConfirmationLink(String emailBody)
    {
        Pattern pattern = Pattern.compile("https?://accounts\\S+");
        Matcher matcher = pattern.matcher(emailBody);
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }
}
