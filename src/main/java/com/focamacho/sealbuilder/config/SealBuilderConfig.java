package com.focamacho.sealbuilder.config;

import blue.endless.jankson.Comment;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

@ConfigSerializable
public class SealBuilderConfig {

    @Comment("Preço padrão para a criação/compra de um pokémon normal.")
    public double normalPokemonPrice = 1000.00;

    @Comment("Preço padrão para a criação/compra de um pokémon lendário.")
    public double legendaryPokemonPrice = 100000.00;

    @Comment("Item que aparece no menu principal quando não há pokémons no slot.")
    public String noPokemonItem = "pixelmon:master_ball";

    @Comment("Item usado como botão cancelar em menus de confirmação.")
    public String cancelItem = "pixelmon:red_apricorn";

    @Comment("Item usado como botão confirmar em menus de confirmação.")
    public String confirmItem = "pixelmon:green_apricorn";

    @Comment("ID da Moeda para ser usada como padrão, exemplo: 'economylite:cash'. Deixe vazio para usar a padrão do servidor.")
    public String currencyId = "";

    @Comment("Preço padrão para a edição da Pokébola em que o Pokémon foi capturado.")
    public double pokeballPrice = 100.00;

    @Comment("Item usado como ícone de alteração de Shiny no menu de edição.")
    public String shinyIcon = "pixelmon:lure_shiny_weak";

    @Comment("Preço padrão para alternar se um Pokémon é Shiny ou não.")
    public double shinyPrice = 3000.00;

    @Comment("Item usado como ícone de alteração de Tamanho no menu de edição.")
    public String growthIcon = "pixelmon:berrytree_kasib";

    @Comment("Preço padrão para alterar o Tamanho de um Pokémon.")
    public double growthPrice = 250.00;

    @Comment("Item usado como ícone de desbloqueio da Habilidade Oculta no menu de edição.")
    public String hiddenAbilityIcon = "pixelmon:lure_ha_weak";

    @Comment("Preço padrão para desbloquear a Habilidade Oculta de um Pokémon.")
    public double hiddenAbilityPrice = 15000.00;

    @Comment("Item usado como ícone de troca de Gênero do Pokémon no menu de edição quando o Pokémon não possui Gênero.")
    public String noGenderIcon = "pixelmon:mint:4";

    @Comment("Item usado como ícone de troca de Gênero do Pokémon no menu de edição quando o Pokémon possui o Gênero masculino.")
    public String maleGenderIcon = "pixelmon:mint:12";

    @Comment("Item usado como ícone de troca de Gênero do Pokémon no menu de edição quando o Pokémon possui o Gênero feminino.")
    public String femaleGenderIcon = "pixelmon:mint:24";

    @Comment("Preço padrão para trocar o Gênero de um Pokémon.")
    public double genderPrice = 3000.00;

    @Comment("Item usado como ícone de troca de Natureza do Pokémon no menu de edição.")
    public String natureIcon = "pixelmon:gracidea";

    @Comment("Item usado como ícone de troca de IVs do Pokémon no menu de edição.")
    public String ivIcon = "pixelmon:ice_gem";

    @Comment("Item usado para representar os status de Ataque do Pokémon nos menus de edição.")
    public String attackIcon = "pixelmon:psychic_gem";

    @Comment("Item usado para representar os status de Defesa do Pokémon nos menus de edição.")
    public String defenceIcon = "pixelmon:water_gem";

    @Comment("Item usado para representar os status de Ataque Especial do Pokémon nos menus de edição.")
    public String spAttackIcon = "pixelmon:fire_gem";

    @Comment("Item usado para representar os status de Defesa Especial do Pokémon nos menus de edição.")
    public String spDefenceIcon = "pixelmon:grass_gem";

    @Comment("Item usado para representar os status de Velocidade do Pokémon nos menus de edição.")
    public String speedIcon = "pixelmon:ice_gem";

    @Comment("Item usado para representar os status de HP do Pokémon nos menus de edição.")
    public String hpIcon = "pixelmon:fairy_gem";

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