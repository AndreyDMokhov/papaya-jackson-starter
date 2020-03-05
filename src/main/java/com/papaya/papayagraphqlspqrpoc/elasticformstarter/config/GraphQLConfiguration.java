package com.papaya.papayagraphqlspqrpoc.elasticformstarter.config;

import com.papaya.papayagraphqlspqrpoc.elasticformstarter.annotation.Graph;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import io.leangen.graphql.GraphQLSchemaGenerator;
import io.leangen.graphql.generator.mapping.TypeMapper;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class GraphQLConfiguration {

    @Autowired
    private ApplicationContext context;

    @Bean
    public GraphQL graphQLConfig() {

        GraphQLSchemaGenerator graphQLSchemaGenerator = new GraphQLSchemaGenerator()
                .withBasePackages("com.papaya.papayagraphqlspqrpoc")
                .withTypeMappers(getTypeMappers());

        Map<Object, ? extends Class<?>> graphs = getGraphs();
        for (Map.Entry<Object, ? extends Class<?>> graph : graphs.entrySet()) {
            graphQLSchemaGenerator.withOperationsFromSingleton(graph.getKey(), graph.getValue());
        }
        GraphQLSchema schema = graphQLSchemaGenerator.generate();

        return GraphQL.newGraphQL(schema).build();
    }

    private TypeMapper[] getTypeMappers() {
        return context.getBeansOfType(TypeMapper.class).values().toArray(new TypeMapper[0]);
    }

    public Map<Object, ? extends Class<?>> getGraphs() {
        Map<String, Object> beansWithAnnotation = context.getBeansWithAnnotation(Graph.class);
        return beansWithAnnotation.entrySet().stream().collect(Collectors.toMap(Map.Entry::getValue, v -> AopUtils.getTargetClass(v.getValue())));
    }
}
