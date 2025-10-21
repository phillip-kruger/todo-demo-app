package io.quarkus.sample.ai;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;
import io.quarkiverse.langchain4j.mcp.runtime.McpToolBox;
import java.time.temporal.ChronoUnit;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Timeout;

@RegisterAiService(retrievalAugmentor = TodoRetrievalAugmentor.class)
public interface TodoAiService {

    @McpToolBox("quarkus-blog")
    @SystemMessage("You are a helpful assistant in a TODO app")
    @UserMessage("""
                 I am busy learning {subject}. I need things to add to my TODO list to learn {subject}.
                 What can I add to my TODO list ? 
                 
                 Answer with only one item for the list, and only that item, no other text. 
                 The item on the list needs to be related to {subject} topics to learn.
                 Do NOT add things like 'You can add' or 'Add a' or 'Learn about' or 'Learn how to' at the start of the response.
                 Do NOT add things like 'to the list' to the end of the response, 
                 just reply with the list item, that can be directly added to the list.
                 
                 Do NOT repeat any previous suggestions.
                 Do NOT put the response in quotes (") or escaped quotes (\"). 
                 Do NOT add a new line (\\n).
                 
                 Use the `current todos` as input of things currently on the list, and do NOT repeat any of them.
                 Use the `current todos` as context on the things being learned and suggest something that would make sense to learn next.
                 Use the latest articles from Quarkus Blog to make sure you suggest relevant topics
            """)
    @Timeout(value = 60, unit = ChronoUnit.SECONDS)
    @Fallback(fallbackMethod = "fallback")
    String suggestSomethingTodo(String subject);
    
    default String fallback(String subject) {
        return "Fix AI integration";
    }
}