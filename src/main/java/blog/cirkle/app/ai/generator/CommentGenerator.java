package blog.cirkle.app.ai.generator;

import blog.cirkle.app.ai.generator.api.CommentResponse;
import blog.cirkle.app.ai.generator.api.PersonDescription;
import blog.cirkle.app.api.rest.model.CommentDto;
import blog.cirkle.app.api.rest.model.PostDto;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.input.PromptTemplate;
import dev.langchain4j.model.mistralai.MistralAiChatModel;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static blog.cirkle.app.ai.generator.GenerationUtils.extractJson;

@Component
@Profile("data-generation")
@RequiredArgsConstructor
public class CommentGenerator {

    private final MistralAiChatModel mistralLargeModel;

    private static PromptTemplate postCommentTemplate = PromptTemplate.from("""
            ### INSTRUCTIONS ###
            Act as {{name}} who is {{description}}
            
            Write a short comment on FB post. Be creative, make direct comment, avoid addressing author by name.
            
            ### POST ###
            Author: {{author}}
            
            {{content}}
            
            ### OUTPUT ###
            Return response in JSON format without additional text before or after with comment filed:
            ```
            {
              "comment": "Your comment"
            }
            ```
            """);

    private static PromptTemplate threadCommentTemplate = PromptTemplate.from("""
            ### INSTRUCTIONS ###
            Act as {{name}} who is {{description}}
            
            Write a short(3-5 words) comment on comment thread of FB post. Comment should be based mostly on the thread, but also stay on original post topic.
            ### POST ###
            Author: {{author}}
            
            {{content}}
            
            ### THREAD ###
            {{thread}}
            
            ### OUTPUT ###
            Return response in JSON format without additional text before or after with comment filed:
            ```
            {
              "comment": "Your comment"
            }
            ```
            """);

    public String commentPost(PersonDescription person, PostDto postDto) {
        UserMessage message = postCommentTemplate.apply(Map.of(
                "name", person.getFullName(),
                "description", person.getBio(),
                "author", postDto.getAuthor().getName(),
                "content", postDto.getText()
        )).toUserMessage();
        CommentResponse commentResponse = extractJson(mistralLargeModel.generate(message).content().text(), CommentResponse.class);
        return commentResponse.getComment();
    }

    public String commentThread(PersonDescription person, PostDto postDto, List<CommentDto> thread) {
        UserMessage message = threadCommentTemplate.apply(Map.of(
                "name", person.getFullName(),
                "description", person.getBio(),
                "author", postDto.getAuthor().getName(),
                "content", postDto.getText(),
                "thread", thread.stream().map(c -> c.getAuthor().getName() + ": " + c.getText()).collect(Collectors.joining("\n"))
        )).toUserMessage();
        CommentResponse commentResponse = extractJson(mistralLargeModel.generate(message).content().text(), CommentResponse.class);
        return commentResponse.getComment();


    }
}
