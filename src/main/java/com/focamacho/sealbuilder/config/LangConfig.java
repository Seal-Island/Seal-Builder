package com.focamacho.sealbuilder.config;

import com.focamacho.sealbuilder.SealBuilder;
import com.focamacho.sealbuilder.util.Paths;
import com.focamacho.seallibrary.util.JsonHandler;
import org.json.JSONObject;
import org.spongepowered.api.item.inventory.Inventory;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LangConfig {

    public static Map<String, String> messages = new HashMap<>();

    public static void initLang() throws IOException {
        File jsonFile = new File(Paths.langFile);
        JSONObject json = JsonHandler.getOrCreateJson(jsonFile);
        Map<String, String> defaultMessages = getDefaultMessages();

        defaultMessages.forEach((key, message) -> {
            try {
                messages.put(key, JsonHandler.getOrCreateString(jsonFile, key, message));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public static String get(String key) {
        return messages.getOrDefault(key, "");
    }

    private static Map<String, String> getDefaultMessages() {
        Map<String, String> defaultMessages = new HashMap<>();
        Inventory.builder().build(SealBuilder.instance);
        //Menu Principal
        defaultMessages.put("menu.main.title", "Clique em um pokémon");
        defaultMessages.put("menu.main.pokemon.name", "&f%pokemonname% &7- &bNível &d%pokemonlvl%");
        defaultMessages.put("menu.main.pokemon.lore", "&7Habilidade: &d%pokemonability%\n&7Natureza: &d%pokemonnature%\n&7Poder Oculto: &d%pokemonhiddenpower%\n&7Gênero: &d%pokemongender%\n&7Tamanho: &d%pokemonsize%\n&7Felicidade: &d%pokemonhappiness%\n&7Shiny: &d%pokemonshiny%\n&7Pokébola Usada: &d%pokemonball%\n\n&7IVs: &d%pokemonivsum%&7/&d186 &7(&d%pokemonivpercentage%%&7)\n&cHP: %pokemonivhp% &7/ &eAtaque: %pokemonivatk% &7/ &9Defesa: %pokemonivdef%\n&bVelocidade: %pokemonivspe% &7/ &6Atq. E: %pokemonivspa% &7/ &3Def. E: %pokemonivspd%\n&7EVs: &d%pokemonevsum%&7/&d510 &7(&d%pokemonevpercentage%%&7)\n&cHP: %pokemonevhp% &7/ &eAtaque: %pokemonevatk% &7/ &9Defesa: %pokemonevdef%\n&bVelocidade: %pokemonevspe% &7/ &6Atq. E: %pokemonevspa% &7/ &3Def. E: %pokemonevspd%\n\n&7Movimentos:\n&7- &b%pokemonmoveone%\n&7- &b%pokemonmovetwo%\n&7- &b%pokemonmovethree%\n&7- &b%pokemonmovefour%\n");
        defaultMessages.put("menu.main.create", "&7Clique aqui para criar um &dPokémon&7.");
        defaultMessages.put("menu.main.nopokemon", "&7Capture um &dPokémon &7primeiro.");

        //Menu de Compra de Pokémon
        defaultMessages.put("menu.create.title", "Comprar pokémon");
        defaultMessages.put("menu.create.cancel.name", "&cCancelar");
        defaultMessages.put("menu.create.cancel.lore", "&7Fechar o menu.");
        defaultMessages.put("menu.create.confirm.name", "&aConfirmar");
        defaultMessages.put("menu.create.confirm.lore", "&7Isso vai lhe custar &c%price% %currencyname%&7.%discountlore%");
        defaultMessages.put("menu.create.pokemon.name", "&d%pokemonname%");
        defaultMessages.put("menu.create.pokemon.lore", "&7Preço: &d&c%price% %currencyname%%blacklistwarn%%discountlore%");
        defaultMessages.put("menu.create.blacklistwarn", "\n&7Atenção! Os seguintes modificadores estão\n&7bloqueados nesse &dPokémon&7:");

        //Menu de Shiny
        defaultMessages.put("menu.shiny.title", "Alternar shiny");
        defaultMessages.put("menu.shiny.cancel.name", "&cCancelar");
        defaultMessages.put("menu.shiny.cancel.lore", "&7Voltar ao menu de edição.");
        defaultMessages.put("menu.shiny.confirm.name", "&aConfirmar");
        defaultMessages.put("menu.shiny.confirm.lore", "&7Isso vai lhe custar &c%price% %currencyname%&7.%discountlore%");
        defaultMessages.put("menu.shiny.pokemon.name", "&d%pokemonname%");
        defaultMessages.put("menu.shiny.pokemon.lore", "&7Preço: &d&c%price% %currencyname%%discountlore%");

        //Menu de Mudança de Gênero
        defaultMessages.put("menu.gender.title", "Alternar gênero");
        defaultMessages.put("menu.gender.cancel.name", "&cCancelar");
        defaultMessages.put("menu.gender.cancel.lore", "&7Voltar ao menu de edição.");
        defaultMessages.put("menu.gender.confirm.name", "&aConfirmar");
        defaultMessages.put("menu.gender.confirm.lore", "&7Isso vai lhe custar &c%price% %currencyname%&7.%discountlore%");
        defaultMessages.put("menu.gender.pokemon.name", "&d%pokemonname%");
        defaultMessages.put("menu.gender.pokemon.lore", "&7Novo Gênero: &d%pokemongender%\n&7Preço: &d&c%price% %currencyname%%discountlore%");

        //Menu de Desbloqueio de Habilidade Oculta
        defaultMessages.put("menu.hiddenability.title", "Desbloquear habilidade oculta");
        defaultMessages.put("menu.hiddenability.cancel.name", "&cCancelar");
        defaultMessages.put("menu.hiddenability.cancel.lore", "&7Voltar ao menu de edição.");
        defaultMessages.put("menu.hiddenability.confirm.name", "&aConfirmar");
        defaultMessages.put("menu.hiddenability.confirm.lore", "&7Isso vai lhe custar &c%price% %currencyname%&7.%discountlore%");
        defaultMessages.put("menu.hiddenability.pokemon.name", "&d%pokemonname%");
        defaultMessages.put("menu.hiddenability.pokemon.lore", "&7Nova Habilidade: &d%pokemonability%\n&7Preço: &d&c%price% %currencyname%%discountlore%");

        //Menu de Edição de Pokébola
        defaultMessages.put("menu.growth.title", "Escolha um novo tamanho");
        defaultMessages.put("menu.growth.lore", "&7Clique para escolher este &aTamanho&7.\n&7Preço: &a%price% %currencyname%%discountlore%");
        defaultMessages.put("menu.growth.your", "&7Este já é o &aTamanho &7atual.");

        //Menu de Edição de Nature
        defaultMessages.put("menu.nature.title", "Escolha uma nova natureza");
        defaultMessages.put("menu.nature.lore", "&a+ %plusstats%\n&c- %minusstats%\n&7Clique para escolher esta &cNatureza&7.\n&7Preço: &a%price% %currencyname%%discountlore%");
        defaultMessages.put("menu.nature.neutral", "&8+- Neutro\n&7Clique para escolher esta &cNatureza&7.\n&7Preço: &a%price% %currencyname%%discountlore%");
        defaultMessages.put("menu.nature.your", "&7Esta já é a &cNatureza &7atual.");
        defaultMessages.put("menu.nature.attack.plus", "&aAumento de Ataque");
        defaultMessages.put("menu.nature.defence.plus", "&aAumento de Defesa");
        defaultMessages.put("menu.nature.spattack.plus", "&aAumento de Ataque Especial");
        defaultMessages.put("menu.nature.spdefence.plus", "&aAumento de Defesa Especial");
        defaultMessages.put("menu.nature.speed.plus", "&aAumento de Velocidade");
        defaultMessages.put("menu.nature.attack.minus", "&cRedução de Ataque");
        defaultMessages.put("menu.nature.defence.minus", "&cRedução de Defesa");
        defaultMessages.put("menu.nature.spattack.minus", "&cRedução de Ataque Especial");
        defaultMessages.put("menu.nature.spdefence.minus", "&cRedução de Defesa Especial");
        defaultMessages.put("menu.nature.speed.minus", "&cRedução de Velocidade");

        //Menu de Edição de IVs
        defaultMessages.put("menu.ivs.title", "Defina seus IVs");
        defaultMessages.put("menu.ivs.hp.plus", "&aAumentar HP");
        defaultMessages.put("menu.ivs.attack.plus", "&aAumentar Ataque");
        defaultMessages.put("menu.ivs.defence.plus", "&aAumentar Defesa");
        defaultMessages.put("menu.ivs.specialattack.plus", "&aAumentar Ataque Especial");
        defaultMessages.put("menu.ivs.specialdefence.plus", "&aAumentar Defesa Especial");
        defaultMessages.put("menu.ivs.speed.plus", "&aAumentar Velocidade");
        defaultMessages.put("menu.ivs.hp.minus", "&cDiminuir HP");
        defaultMessages.put("menu.ivs.attack.minus", "&cDiminuir Ataque");
        defaultMessages.put("menu.ivs.defence.minus", "&cDiminuir Defesa");
        defaultMessages.put("menu.ivs.specialattack.minus", "&cDiminuir Ataque Especial");
        defaultMessages.put("menu.ivs.specialdefence.minus", "&cDiminuir Defesa Especial");
        defaultMessages.put("menu.ivs.speed.minus", "&cDiminuir Velocidade");
        defaultMessages.put("menu.ivs.limit.plus", "&7Limite máximo atingido.");
        defaultMessages.put("menu.ivs.limit.minus", "&7Limite mínimo atingido.");
        defaultMessages.put("menu.ivs.lore.plus", "&7Clique para aumentar este &bIV &7em &b1 &7por &a%price%&7.\n&7Shift + Clique para aumentar este &bIV &7em &b10 &7por &a%priceiv10% %currencyname%&7.%discountlore%");
        defaultMessages.put("menu.ivs.lore.minus", "&7Clique para diminuir este &bIV &7em &b1 &7por &a%price%&7.\n&7Shift + Clique para diminuir este &bIV &7em &b10 &7por &a%priceiv10% %currencyname%&7.%discountlore%");
        defaultMessages.put("menu.ivs.max.name", "&bIVs &dmáximos");
        defaultMessages.put("menu.ivs.max.lore", "&7Clique para deixar todos os &bIVs &7do\n&7seu &dPokémon &7no máximo por &a%ivmaxprice% %currencyname%&7.%discountlore%");
        defaultMessages.put("menu.ivs.random.name", "&bIVs &caleatórios");
        defaultMessages.put("menu.ivs.random.lore", "&7Clique para randomizar todos os &bIVs &7do\n&7seu &dPokémon &7por &a%price% %currencyname%&7.\n&7(3 tipos ficarão no nível máximo)%discountlore%");

        //Menu de Edição de Pokémon
        defaultMessages.put("menu.edit.title", "Clique em algo para editar");
        defaultMessages.put("menu.edit.pokeball.name", "&fPokébola");
        defaultMessages.put("menu.edit.pokeball.lore", "&7Clique para editar a &dPokébola &7em\n&7que o &dPokémon &7foi capturado.");
        defaultMessages.put("menu.edit.shiny.name", "&eShiny");
        defaultMessages.put("menu.edit.shiny.lore", "&7Clique para alternar se o &dPokémon\n&7é &eShiny &7ou não.");
        defaultMessages.put("menu.edit.growth.name", "&aTamanho");
        defaultMessages.put("menu.edit.growth.lore", "&7Clique para alterar o &aTamanho\n&7do &dPokémon&7.");
        defaultMessages.put("menu.edit.hiddenability.name", "&5Habilidade Oculta");
        defaultMessages.put("menu.edit.hiddenability.lore", "&7Clique para liberar a &5Habilidade&7\n&5Oculta &7do seu &dPokémon&7.");
        defaultMessages.put("menu.edit.hiddenability.your", "&7Seu &dPokémon &7já possui a\n&7sua &5Habilidade Oculta&7.");
        defaultMessages.put("menu.edit.hiddenability.none", "&7Esse &dPokémon &7não possui\n&7uma &5Habilidade Oculta&7.");
        defaultMessages.put("menu.edit.gender.name", "&dGênero");
        defaultMessages.put("menu.edit.gender.lore", "&7Clique para alternar o &dGênero\n&7do seu &dPokémon&7.");
        defaultMessages.put("menu.edit.gender.nogender", "&7Este &dPokémon &7só possui\n&7um único &dGênero&7.");
        defaultMessages.put("menu.edit.nature.name", "&cNatureza");
        defaultMessages.put("menu.edit.nature.lore", "&7Clique para alterar a &cNatureza\n&7do seu &dPokémon&7.");
        defaultMessages.put("menu.edit.ivs.name", "&bIVs");
        defaultMessages.put("menu.edit.ivs.lore", "&7Clique para alterar os &bIVs\n&7do seu &dPokémon&7.");

        //Menu de Edição de Pokébola
        defaultMessages.put("menu.pokeball.title", "Escolha uma nova pokébola");
        defaultMessages.put("menu.pokeball.lore", "&7Clique para escolher essa &dPokébola&7.\n&7Preço: &a%price% %currencyname%%discountlore%");
        defaultMessages.put("menu.pokeball.your", "&7Essa já é a sua &dPokébola&7.");

        //Mensagens no Chat
        defaultMessages.put("chat.prefix", "&7[&dSeal Builder&7] ");
        defaultMessages.put("chat.nopokemon", "&7Não foi possível encontrar nenhum &dPokémon &7com o nome informado.");
        defaultMessages.put("chat.money.insufficient", "&7Você não possui %currencyname% suficientes para fazer isso.");
        defaultMessages.put("chat.buy.success", "&7Você adquiriu o &dPokémon &7com sucesso.");
        defaultMessages.put("chat.create", "&7Digite o nome do &dPokémon &7que você deseja.");
        defaultMessages.put("chat.edit.pokeball", "&7Você alterou a &dPokébola &7do seu &dPokémon &7com sucesso.");
        defaultMessages.put("chat.create.blacklist", "&7Desculpe, não é permitido a criação deste tipo de &dPokémon&7.");
        defaultMessages.put("chat.shiny.blacklist", "&7Desculpe, não é permitido a edição do &eShiny &7deste tipo de &dPokémon&7.");
        defaultMessages.put("chat.growth.blacklist", "&7Desculpe, não é permitido a edição do &aTamanho &7deste tipo de &dPokémon&7.");
        defaultMessages.put("chat.pokeball.blacklist", "&7Desculpe, não é permitido a edição da &dPokébola &7deste tipo de &dPokémon&7.");
        defaultMessages.put("chat.edit.shiny", "&7Você alterou o &eShiny &7do seu &dPokémon &7com sucesso.");
        defaultMessages.put("chat.edit.growth", "&7Você alterou o &aTamanho &7do seu &dPokémon &7com sucesso.");
        defaultMessages.put("chat.hiddenability.blacklist", "&7Desculpe, não é permitido o desbloqueio da &5Habilidade Oculta &7deste tipo de &dPokémon&7.");
        defaultMessages.put("chat.edit.hiddenability", "&7Você desbloqueou a &5Habilidade Oculta &7do seu &dPokémon &7com sucesso.");
        defaultMessages.put("chat.edit.gender", "&7Você alterou o &dGênero &7do seu &dPokémon &7com sucesso.");
        defaultMessages.put("chat.gender.blacklist", "&7Desculpe, não é permitido a troca de &dGênero &7deste tipo de &dPokémon&7.");
        defaultMessages.put("chat.edit.nature", "&7Você alterou a &cNatureza &7do seu &dPokémon &7com sucesso.");
        defaultMessages.put("chat.nature.blacklist", "&7Desculpe, não é permitido a troca de &cNatureza &7deste tipo de &dPokémon&7.");
        defaultMessages.put("chat.modifier.disabled", "&7Desculpe, esse modificador foi desativado no servidor.");
        defaultMessages.put("chat.ivs.limit.max", "&7A quantia de pontos desejada ultrapassa o limite máximo.");
        defaultMessages.put("chat.ivs.limit.min", "&7A quantia de pontos desejada ultrapassa o limite mínimo.");
        defaultMessages.put("chat.ivs.plus", "&7O &bIV &7do seu &dPokémon &7foi aumentado.");
        defaultMessages.put("chat.ivs.minus", "&7O &bIV &7do seu &dPokémon &7foi diminuido.");
        defaultMessages.put("chat.ivs.blacklist", "&7Desculpe, não é permitido a alteração dos &bIVs &7deste tipo de &dPokémon&7.");
        defaultMessages.put("chat.ivs.random", "&7Os &bIVs &7do seu &dPokémon &7foram randomizados.");
        defaultMessages.put("chat.onlyplayer", "&7Somente jogadores podem usar esse comando.");
        defaultMessages.put("chat.nopermission", "&7Você não tem permissão para fazer isso.");
        defaultMessages.put("chat.nohidden", "&7Esse pokémon não possui uma &5Habilidade Oculta&7.");

        //Básico
        defaultMessages.put("yes", "Sim");
        defaultMessages.put("no", "Não");
        defaultMessages.put("none", "Nenhum");

        //Gêneros
        defaultMessages.put("gender.male", "Masculino");
        defaultMessages.put("gender.female", "Feminino");
        defaultMessages.put("gender.none", "Sem Gênero");

        //Tamanhos
        defaultMessages.put("growth.microscopic", "Microscópico");
        defaultMessages.put("growth.pygmy", "Pigmeu");
        defaultMessages.put("growth.runt", "Nanico");
        defaultMessages.put("growth.small", "Pequeno");
        defaultMessages.put("growth.ordinary", "Comum");
        defaultMessages.put("growth.huge", "Imenso");
        defaultMessages.put("growth.giant", "Gigante");
        defaultMessages.put("growth.enormous", "Enorme");
        defaultMessages.put("growth.ginormous", "Gigantesco");

        //Status
        defaultMessages.put("statstype.none", "Nenhum");
        defaultMessages.put("statstype.hp", "HP");
        defaultMessages.put("statstype.attack", "Ataque");
        defaultMessages.put("statstype.defence", "Defesa");
        defaultMessages.put("statstype.specialattack", "Ataque Especial");
        defaultMessages.put("statstype.specialdefence", "Defesa Especial");
        defaultMessages.put("statstype.speed", "Velocidade");
        defaultMessages.put("statstype.accuracy", "Precisão");
        defaultMessages.put("statstype.evasion", "Evasão");

        //Status "Compactados"
        defaultMessages.put("statstype.none.compact", "Nenhum");
        defaultMessages.put("statstype.hp.compact", "HP");
        defaultMessages.put("statstype.attack.compact", "Ataq.");
        defaultMessages.put("statstype.defence.compact", "Def.");
        defaultMessages.put("statstype.specialattack.compact", "Ataq. E.");
        defaultMessages.put("statstype.specialdefence.compact", "Def. E.");
        defaultMessages.put("statstype.speed.compact", "Veloc.");
        defaultMessages.put("statstype.accuracy.compact", "Precis.");
        defaultMessages.put("statstype.evasion.compact", "Evas.");

        //Nomes de Modifier
        defaultMessages.put("modifier.growth", "&cTamanho");
        defaultMessages.put("modifier.shiny", "&cShiny");
        defaultMessages.put("modifier.pokeball", "&cPokébola");
        defaultMessages.put("modifier.ivs", "&cIVs");
        defaultMessages.put("modifier.nature", "&cNature");
        defaultMessages.put("modifier.gender", "&cGênero");
        defaultMessages.put("modifier.hiddenability", "&cHabilidade Oculta");

        //Outros
        defaultMessages.put("discount.applied", "&7&oJá foi aplicado no valor o seu desconto de %discount%.");

        return defaultMessages;
    }

}
