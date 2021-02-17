package com.focamacho.sealbuilder.config;

import com.focamacho.sealconfig.relocated.blue.endless.jankson.Comment;

public class SealBuilderConfig {

    @Comment("Os Aliases usados para o comando que abre o PokeBuilder.")
    public String[] builderAliases = {"builder", "pokebuilder"};

    @Comment("Preço padrão para a criação/compra de um pokémon normal.")
    public double normalPokemonPrice = 1000.00;

    @Comment("Preço padrão para a criação/compra de um pokémon lendário.")
    public double legendaryPokemonPrice = 100000.00;

    @Comment("ID da Moeda para ser usada como padrão, exemplo: 'economylite:cash'. Deixe vazio para usar a padrão do servidor.\n" +
            "Só funciona em sistemas de múltiplas moedas no Sponge!")
    public String currencyId = "";

    @Comment("Preço padrão para a edição da Pokébola em que o Pokémon foi capturado.")
    public double pokeballPrice = 100.00;

    @Comment("Preço padrão para alternar se um Pokémon é Shiny ou não.")
    public double shinyPrice = 3000.00;

    @Comment("Preço padrão para alterar o Tamanho de um Pokémon.")
    public double growthPrice = 250.00;

    @Comment("Preço padrão para desbloquear a Habilidade Oculta de um Pokémon.")
    public double hiddenAbilityPrice = 15000.00;

    @Comment("Preço padrão para trocar o Gênero de um Pokémon.")
    public double genderPrice = 3000.00;

    @Comment("Preço padrão para trocar a Natureza de um Pokémon.")
    public double naturePrice = 10000.00;

    @Comment("Preço padrão para alterar o IV de um Pokémon em 1 ponto.")
    public double ivPrice = 300.00;

    @Comment("Preço para randomizar todos os IVs de um Pokémon. (3 virão sempre no máximo)")
    public double randomIvPrice = 6000.00;

    @Comment("Preço de cada ponto ao escolher usar a opção de aumentar o IV ao máximo.")
    public double ivMaxPrice = 250.00;

    @Comment("Idioma das mensagens. O nome deve ser o mesmo do arquivo localizado na pasta lang.")
    public String defaultLang = "pt_br";

}