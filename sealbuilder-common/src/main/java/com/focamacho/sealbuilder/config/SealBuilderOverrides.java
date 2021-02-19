package com.focamacho.sealbuilder.config;

import com.focamacho.sealconfig.relocated.blue.endless.jankson.Comment;

import java.util.HashMap;
import java.util.Map;

public class SealBuilderOverrides {

    public SealBuilderOverrides() {
        PriceOverride exampleOverride = new PriceOverride();
        exampleOverride.pokemon = "mew";
        exampleOverride.price = 30;
        exampleOverride.currency = "economylite:cash";
        createPriceOverrides = new PriceOverride[]{exampleOverride};

        PriceOverride exampleShinyOverride = new PriceOverride();
        exampleShinyOverride.pokemon = "legendary";
        exampleShinyOverride.price = 10;
        exampleShinyOverride.currency = "economylite:cash";
        shinyPriceOverrides = new PriceOverride[]{exampleShinyOverride};

        Map<String, String[]> blacklistExample = new HashMap<>();
        blacklistExample.put("ditto", new String[]{"ivs", "nature"});
        blacklist = blacklistExample;
    }

    @Comment("Informação: Acesse https://github.com/Seal-Island/Seal-Builder#configuração para ter algumas informações úteis\n" +
            "que podem lhe ajudar na configuração dos overrides.\n" +
            "\n" +
            "Defina aqui as sobreposições de preços para criação de pokémons específicos.\n" +
            "O formato é:\n" +
            "{\n" +
            "   \"pokemon\": \"nomeDoPokemon\",\n" +
            "   \"price\": \"preçoDoPokemon\"\n" +
            "   \"currency\": \"moedaUsada\"\n" +
            "}\n" +
            "Atenção: o valor currency é opcional e somente funciona em sistemas de múltiplas moedas\n" +
            "do Sponge!\n" +
            "Em \"nomeDoPokemon\" você pode usar a palavra \"legendary\" para se referenciar a todos\n" +
            "os pokémons lendários.")
    public PriceOverride[] createPriceOverrides;

    @Comment("Defina aqui as sobreposições de preços para alternar o Shiny de um pokémon.\n" +
            "O formato é:\n" +
            "{\n" +
            "   \"pokemon\": \"nomeDoPokemon\",\n" +
            "   \"price\": \"preçoDoShiny\"\n" +
            "   \"currency\": \"moedaUsada\"\n" +
            "}\n" +
            "Atenção: o valor currency é opcional e somente funciona em sistemas de múltiplas moedas\n" +
            "do Sponge!\n" +
            "Em \"nomeDoPokemon\" você pode usar a palavra \"legendary\" para se referenciar a todos\n" +
            "os pokémons lendários.")
    public PriceOverride[] shinyPriceOverrides;

    @Comment("Defina aqui as sobreposições de preços para desbloquear a Habilidade Oculta de um pokémon.\n" +
            "O formato é:\n" +
            "{\n" +
            "   \"pokemon\": \"nomeDoPokemon\",\n" +
            "   \"price\": \"preçoDaHabilidadeOculta\"\n" +
            "   \"currency\": \"moedaUsada\"\n" +
            "}\n" +
            "Atenção: o valor currency é opcional e somente funciona em sistemas de múltiplas moedas\n" +
            "do Sponge!\n" +
            "Em \"nomeDoPokemon\" você pode usar a palavra \"legendary\" para se referenciar a todos\n" +
            "os pokémons lendários.")
    public PriceOverride[] hiddenAbilityPriceOverrides = {};

    @Comment("Defina aqui as sobreposições de preços para alternar o Gênero de um pokémon.\n" +
            "O formato é:\n" +
            "{\n" +
            "   \"pokemon\": \"nomeDoPokemon\",\n" +
            "   \"price\": \"preçoDoGênero\"\n" +
            "   \"currency\": \"moedaUsada\"\n" +
            "}\n" +
            "Atenção: o valor currency é opcional e somente funciona em sistemas de múltiplas moedas\n" +
            "do Sponge!\n" +
            "Em \"nomeDoPokemon\" você pode usar a palavra \"legendary\" para se referenciar a todos\n" +
            "os pokémons lendários.")
    public PriceOverride[] genderPriceOverrides = {};

    @Comment("Defina aqui as sobreposições de preços para alterar a Nature de um pokémon.\n" +
            "O formato é:\n" +
            "{\n" +
            "   \"pokemon\": \"nomeDoPokemon\",\n" +
            "   \"value\": \"nomeDaNature\"\n" +
            "   \"price\": \"preçoDaNature\"\n" +
            "   \"currency\": \"moedaUsada\"\n" +
            "}\n" +
            "Atenção: o valor currency é opcional e somente funciona em sistemas de múltiplas moedas\n" +
            "do Sponge!\n" +
            "Em \"nomeDoPokemon\" você pode usar a palavra \"legendary\" para se referenciar a todos\n" +
            "os pokémons lendários.\n" +
            "Você pode não definir a Nature em value para se referenciar a todas as Natures para esse\n" +
            "pokémon.\n" +
            "Você pode não definir o Pokemon para se referenciar a todos os Pokémons.")
    public ValuePriceOverride[] naturePriceOverrides = {};

    @Comment("Defina aqui as sobreposições de preços para alterar a Pokébola de um pokémon.\n" +
            "O formato é:\n" +
            "{\n" +
            "   \"pokemon\": \"nomeDoPokemon\",\n" +
            "   \"value\": \"nomeDaPokébola\"\n" +
            "   \"price\": \"preçoDaPokébola\"\n" +
            "   \"currency\": \"moedaUsada\"\n" +
            "}\n" +
            "Atenção: o valor currency é opcional e somente funciona em sistemas de múltiplas moedas\n" +
            "do Sponge!\n" +
            "Em \"nomeDoPokemon\" você pode usar a palavra \"legendary\" para se referenciar a todos\n" +
            "os pokémons lendários.\n" +
            "Você pode não definir a Pokébola em value para se referenciar a todas as Pokébolas para esse\n" +
            "pokémon.\n" +
            "Você pode não definir o Pokemon para se referenciar a todos os Pokémons.")
    public ValuePriceOverride[] pokeballPriceOverrides = {};

    @Comment("Defina aqui as sobreposições de preços para alterar o Tamanho de um pokémon.\n" +
            "O formato é:\n" +
            "{\n" +
            "   \"pokemon\": \"nomeDoPokemon\",\n" +
            "   \"value\": \"nomeDoTamanho\"\n" +
            "   \"price\": \"preçoDoTamanho\"\n" +
            "   \"currency\": \"moedaUsada\"\n" +
            "}\n" +
            "Atenção: o valor currency é opcional e somente funciona em sistemas de múltiplas moedas\n" +
            "do Sponge!\n" +
            "Em \"nomeDoPokemon\" você pode usar a palavra \"legendary\" para se referenciar a todos\n" +
            "os pokémons lendários.\n" +
            "Você pode não definir o Tamanho em value para se referenciar a todas os Tamanhos para esse\n" +
            "pokémon.\n" +
            "Você pode não definir o Pokemon para se referenciar a todos os Pokémons.")
    public ValuePriceOverride[] growthPriceOverrides = {};

    @Comment("Defina aqui as sobreposições de preços para alterar o IV de um pokémon.\n" +
            "O formato é:\n" +
            "{\n" +
            "   \"pokemon\": \"nomeDoPokemon\",\n" +
            "   \"value\": \"tipoDeIv\",\n" +
            "   \"price\": \"preçoDoPontoDeIV\"\n" +
            "   \"currency\": \"moedaUsada\"\n" +
            "}\n" +
            "Atenção: o valor currency é opcional e somente funciona em sistemas de múltiplas moedas\n" +
            "do Sponge!\n" +
            "Em \"nomeDoPokemon\" você pode usar a palavra \"legendary\" para se referenciar a todos\n" +
            "os pokémons lendários.\n" +
            "Você pode não definir o Tipo de IV em value para se referenciar a todas os IVs para esse\n" +
            "pokémon.\n" +
            "Você pode não definir o Pokemon para se referenciar a todos os Pokémons.")
    public ValuePriceOverride[] ivsPriceOverrides = {};

    @Comment("Defina aqui as blacklists de modifiers para os Pokémons.\n" +
            "O formato é:\n" +
            "{ \"nomeDoPokemon\": [\"nomeDoModifier\", \"nomeDoModifier2\", ...] }\n" +
            "Em \"nomeDoPokemon\" você pode usar a palavra \"legendary\" para se referenciar a todos\n" +
            "os pokémons lendários, ou a palavra \"all\" para se referenciar a todos os pokémons.\n")
    public Map<String, String[]> blacklist;

    public static class PriceOverride {
        public String pokemon;
        public double price;
        public String currency = "";
    }

    public static class ValuePriceOverride {
        public String pokemon;
        public double price;
        public String value;
        public String currency = "";
    }

}
