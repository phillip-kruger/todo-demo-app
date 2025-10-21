package io.quarkus.sample.ai.mcpserver;

import io.quarkiverse.mcp.server.TextContent;
import io.quarkiverse.mcp.server.Tool;
import io.quarkiverse.mcp.server.ToolArg;
import io.quarkiverse.mcp.server.ToolResponse;
import io.quarkus.sample.Todo;
import io.quarkus.sample.TodoResource;
import jakarta.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

public class TodoMCPResource {

    @Inject 
    TodoResource todoResource;
    
    @Tool(description = "Get my todo list") 
    ToolResponse getTodos(@ToolArg(description = "Include completed items", defaultValue = "false") boolean includeCompleted) { 
        return ToolResponse.success(
                new TextContent(getToolsAsString(includeCompleted)));
    }
    
    @Tool(description = "Mark a todo as completed")
    ToolResponse completeTodos(@ToolArg(description = "Todo title") String title) { 
        
        Todo todo = Todo.findByTitle(title);
        if(todo!=null){
            todo.completed = true;
            todoResource.update(todo, todo.id);
            return ToolResponse.success(
                new TextContent(getToolsAsString(false)));
        }else{
            return ToolResponse.error("Todo with title " + title + " not found");
        }
    }
    
    private String getToolsAsString(boolean includeCompleted){
        List<Todo> all = todoResource.getAll();
        
        if(includeCompleted){
            return todoToString(all);
        }else{
            List<Todo> notCompleted = all.stream()
                .filter(todo -> !todo.completed)
                .toList();
            return todoToString(notCompleted);
        }
    }
    
    private String todoToString(List<Todo> todos){
        return todos.stream()
            .map(t -> t.title)
            .collect(Collectors.joining(", "));
    }
    
}
