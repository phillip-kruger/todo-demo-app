package io.quarkus.sample;

import io.quarkus.sample.ai.TodoAiService;
//import io.quarkus.scheduler.Scheduled;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.openapi.annotations.Operation;

@Path("/api")
public class TodoAIResource {

    @Inject
    TodoAiService ai;
  
    @GET
    @Path("/suggest")
    @Operation(description = "Suggest something to do")
    @Transactional
    public Todo suggest() {
        Todo suggestion = new Todo();

        String title = ai.suggestSomethingTodo("Features of my TODO list application");
        title = title.trim();
        suggestion.title = title;
        suggestion.persistAndFlush();
        return suggestion;
    }
    
    //@Scheduled(every = "60s")
    //void everySecondSuggestATodo() {
    //    Todo suggestion = suggest();
    //    
    //    System.err.println("Maybe you should add this todo to your list: " + suggestion.title);
    //    
    //}
    
}
