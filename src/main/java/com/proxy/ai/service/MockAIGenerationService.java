package com.proxy.ai.service;

import com.proxy.ai.dto.GenerationRequest;
import com.proxy.ai.dto.GenerationResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class MockAIGenerationService implements AIGenerationService {

    private static final List<String> RESPONSES = List.of(
        "La inteligencia artificial es una rama de la informática que busca crear sistemas capaces de realizar tareas que normalmente requieren inteligencia humana, como el aprendizaje, el razonamiento y la resolución de problemas.",
        "El aprendizaje automático permite a los sistemas aprender y mejorar automáticamente a partir de la experiencia sin ser explícitamente programados, centrándose en el desarrollo de programas informáticos que puedan acceder a datos y utilizarlos para aprender por sí mismos.",
        "Las redes neuronales artificiales son sistemas de procesamiento de información inspirados en la forma en que el cerebro biológico procesa la información, compuestos por un gran número de elementos interconectados llamados neuronas.",
        "El procesamiento del lenguaje natural es una subdisciplina de la inteligencia artificial que trabaja en la interacción entre ordenadores y el lenguaje humano, permitiendo a los ordenadores comprender, interpretar y generar lenguaje humano.",
        "La visión por computadora es un campo interdisciplinario que trata de cómo las computadoras pueden obtener comprensión de alto nivel de imágenes o videos digitales, replicando las capacidades del sistema visual humano.",
        "Los modelos de lenguaje grandes han revolucionado el campo del procesamiento del lenguaje natural al demostrar capacidades emergentes sorprendentes, incluyendo la capacidad de generar texto coherente y relevante en respuesta a indicaciones.",
        "La ética en inteligencia artificial es un conjunto de valores, principios y técnicas que utilizan estándares morales generalmente aceptados para guiar el desarrollo y uso responsable de la tecnología de IA.",
        "El aprendizaje por refuerzo es un área del aprendizaje automático donde un agente aprende a comportarse en un entorno realizando acciones y viendo los resultados, optimizando para maximizar alguna noción de recompensa acumulativa.",
        "Los transformers son una arquitectura de modelo de aprendizaje automático introducida en el artículo Attention is All You Need, que ha demostrado ser extremadamente efectiva para muchas tareas de procesamiento del lenguaje natural.",
        "La computación cuántica tiene el potencial de acelerar significativamente ciertos algoritmos de inteligencia artificial, especialmente aquellos relacionados con la optimización y el aprendizaje automático."
    );

    private final Random random = new Random();

    @Override
    public GenerationResponse generate(GenerationRequest request) {
        long start = System.currentTimeMillis();
        try {
            Thread.sleep(1200);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        String responseText = RESPONSES.get(random.nextInt(RESPONSES.size()));
        int tokensUsed = estimateTokens(request.getPrompt()) + estimateTokens(responseText);
        return new GenerationResponse(responseText, tokensUsed, System.currentTimeMillis() - start);
    }

    private int estimateTokens(String text) {
        if (text == null || text.isEmpty()) return 0;
        return (int) Math.ceil(text.length() / 4.0);
    }
}
