package com.focamacho.sealbuilder.config;

import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

@ConfigSerializable
public class PluginConfig {

    @Setting(comment = "Permite a criação/compra de pokémons.")
    public static boolean createPokemon = true;

    @Setting(comment = "Preço padrão para a criação/compra de um pokémon normal.")
    public static double normalPokemonPrice = 1000.00;

    @Setting(comment = "Preço padrão para a criação/compra de um pokémon lendário.")
    public static double legendaryPokemonPrice = 100000.00;

    @Setting(comment = "Item que aparece no menu principal quando não há pokémons no slot.")
    public static String noPokemonItem = "pixelmon:master_ball";

    @Setting(comment = "Permissão necessária para criar um pokémon. Deixe em branco para não precisar de permissão.")
    public static String createPokemonPermission = "";

    @Setting(comment = "Item usado como botão cancelar em menus de confirmação.")
    public static String cancelItem = "pixelmon:red_apricorn";

    @Setting(comment = "Item usado como botão confirmar em menus de confirmação.")
    public static String confirmItem = "pixelmon:green_apricorn";

    @Setting(comment = "ID da Moeda para ser usada como padrão, exemplo: 'economylite:cash'. Deixe vazio para usar a padrão do servidor.")
    public static String currencyId = "";

    @Setting(comment = "Preço padrão para a edição da Pokébola em que o Pokémon foi capturado.")
    public static double pokeballPrice = 100.00;

    @Setting(comment = "Item usado como ícone de alteração de Shiny no menu de edição.")
    public static String shinyIcon = "pixelmon:lure_shiny_weak";

    @Setting(comment = "Preço padrão para alternar se um Pokémon é Shiny ou não.")
    public static double shinyPrice = 3000.00;

    @Setting(comment = "Item usado como ícone de alteração de Tamanho no menu de edição.")
    public static String growthIcon = "pixelmon:berrytree_kasib";

    @Setting(comment = "Preço padrão para alterar o Tamanho de um Pokémon.")
    public static double growthPrice = 250.00;

    @Setting(comment = "Item usado como ícone de desbloqueio da Habilidade Oculta no menu de edição.")
    public static String hiddenAbilityIcon = "pixelmon:lure_ha_weak";

    @Setting(comment = "Preço padrão para desbloquear a Habilidade Oculta de um Pokémon.")
    public static double hiddenAbilityPrice = 15000.00;

    @Setting(comment = "Item usado como ícone de troca de Gênero do Pokémon no menu de edição quando o Pokémon não possui Gênero.")
    public static String noGenderIcon = "pixelmon:mint:4";

    @Setting(comment = "Item usado como ícone de troca de Gênero do Pokémon no menu de edição quando o Pokémon possui o Gênero masculino.")
    public static String maleGenderIcon = "pixelmon:mint:12";

    @Setting(comment = "Item usado como ícone de troca de Gênero do Pokémon no menu de edição quando o Pokémon possui o Gênero feminino.")
    public static String femaleGenderIcon = "pixelmon:mint:24";

    @Setting(comment = "Preço padrão para trocar o Gênero de um Pokémon.")
    public static double genderPrice = 3000.00;

    @Setting(comment = "Item usado como ícone de troca de Natureza do Pokémon no menu de edição.")
    public static String natureIcon = "pixelmon:gracidea";

    @Setting(comment = "Item usado como ícone de troca de IVs do Pokémon no menu de edição.")
    public static String ivIcon = "pixelmon:ice_gem";

    @Setting(comment = "Item usado para representar os status de Ataque do Pokémon nos menus de edição.")
    public static String attackIcon = "pixelmon:psychic_gem";

    @Setting(comment = "Item usado para representar os status de Defesa do Pokémon nos menus de edição.")
    public static String defenceIcon = "pixelmon:water_gem";

    @Setting(comment = "Item usado para representar os status de Ataque Especial do Pokémon nos menus de edição.")
    public static String spAttackIcon = "pixelmon:fire_gem";

    @Setting(comment = "Item usado para representar os status de Defesa Especial do Pokémon nos menus de edição.")
    public static String spDefenceIcon = "pixelmon:grass_gem";

    @Setting(comment = "Item usado para representar os status de Velocidade do Pokémon nos menus de edição.")
    public static String speedIcon = "pixelmon:ice_gem";

    @Setting(comment = "Item usado para representar os status de HP do Pokémon nos menus de edição.")
    public static String hpIcon = "pixelmon:fairy_gem";

    @Setting(comment = "Preço padrão para trocar a Natureza de um Pokémon.")
    public static double naturePrice = 10000.00;

    @Setting(comment = "Preço padrão para alterar o IV de um Pokémon em 1 ponto.")
    public static double ivPrice = 300.00;

    @Setting(comment = "Preço para randomizar todos os IVs de um Pokémon. (3 virão sempre no máximo)")
    public static double randomIvPrice = 6000.00;

    @Setting(comment = "Preço de cada ponto ao escolher usar a opção de aumentar o IV ao máximo.")
    public static double ivMaxPrice = 250.00;

}