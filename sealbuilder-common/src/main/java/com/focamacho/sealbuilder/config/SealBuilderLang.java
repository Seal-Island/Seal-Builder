package com.focamacho.sealbuilder.config;

import com.focamacho.sealbuilder.SealBuilder;
import com.focamacho.seallibrary.config.ILangConfig;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class SealBuilderLang implements ILangConfig {

    @Override
    public String getDefaultLang() {
        return SealBuilder.config.defaultLang;
    }

    @Override
    public Map<String, LinkedHashMap<String, String>> getDefaultTranslations() {
        Map<String, LinkedHashMap<String, String>> translations = new HashMap<>();
        
        LinkedHashMap<String, String> pt_br = new LinkedHashMap<>();

        //PT-BR
        //Menu Principal
        pt_br.put("menu.main.title", "Clique em um pokémon");
        pt_br.put("menu.main.pokemon.name", "&f%pokemonname% &7- &bNível &d%pokemonlvl%");
        pt_br.put("menu.main.pokemon.lore", "&7Habilidade: &d%pokemonability%\n&7Natureza: &d%pokemonnature%\n&7Poder Oculto: &d%pokemonhiddenpower%\n&7Gênero: &d%pokemongender%\n&7Tamanho: &d%pokemonsize%\n&7Felicidade: &d%pokemonhappiness%\n&7Shiny: &d%pokemonshiny%\n&7Pokébola Usada: &d%pokemonball%\n\n&7IVs: &d%pokemonivsum%&7/&d186 &7(&d%pokemonivpercentage%%&7)\n&cHP: %pokemonivhp% &7/ &eAtaque: %pokemonivatk% &7/ &9Defesa: %pokemonivdef%\n&bVelocidade: %pokemonivspe% &7/ &6Atq. E: %pokemonivspa% &7/ &3Def. E: %pokemonivspd%\n&7EVs: &d%pokemonevsum%&7/&d510 &7(&d%pokemonevpercentage%%&7)\n&cHP: %pokemonevhp% &7/ &eAtaque: %pokemonevatk% &7/ &9Defesa: %pokemonevdef%\n&bVelocidade: %pokemonevspe% &7/ &6Atq. E: %pokemonevspa% &7/ &3Def. E: %pokemonevspd%\n\n&7Movimentos:\n&7- &b%pokemonmoveone%\n&7- &b%pokemonmovetwo%\n&7- &b%pokemonmovethree%\n&7- &b%pokemonmovefour%\n");
        pt_br.put("menu.main.create", "&7Clique aqui para criar um &dPokémon&7.");
        pt_br.put("menu.main.nopokemon", "&7Capture um &dPokémon &7primeiro.");

        //Menu de Compra de Pokémon
        pt_br.put("menu.create.title", "Comprar pokémon");
        pt_br.put("menu.create.cancel.name", "&cCancelar");
        pt_br.put("menu.create.cancel.lore", "&7Fechar o menu.");
        pt_br.put("menu.create.confirm.name", "&aConfirmar");
        pt_br.put("menu.create.confirm.lore", "&7Isso vai lhe custar &c$%price% %currencyname%&7.%blacklistwarn%");
        pt_br.put("menu.create.pokemon.name", "&d%pokemonname%");
        pt_br.put("menu.create.pokemon.lore", "&7Preço: &d&c$%price% %currencyname%%blacklistwarn%");
        pt_br.put("menu.create.blacklistwarn", "\n&7Atenção! Os seguintes modificadores estão\n&7bloqueados nesse &dPokémon&7:");

        //Menu de Shiny
        pt_br.put("menu.shiny.title", "Alternar shiny");
        pt_br.put("menu.shiny.cancel.name", "&cCancelar");
        pt_br.put("menu.shiny.cancel.lore", "&7Voltar ao menu de edição.");
        pt_br.put("menu.shiny.confirm.name", "&aConfirmar");
        pt_br.put("menu.shiny.confirm.lore", "&7Isso vai lhe custar &c$%price% %currencyname%&7.");
        pt_br.put("menu.shiny.pokemon.name", "&d%pokemonname%");
        pt_br.put("menu.shiny.pokemon.lore", "&7Preço: &d&c$%price% %currencyname%");

        //Menu de Mudança de Gênero
        pt_br.put("menu.gender.title", "Alternar gênero");
        pt_br.put("menu.gender.cancel.name", "&cCancelar");
        pt_br.put("menu.gender.cancel.lore", "&7Voltar ao menu de edição.");
        pt_br.put("menu.gender.confirm.name", "&aConfirmar");
        pt_br.put("menu.gender.confirm.lore", "&7Isso vai lhe custar &c$%price% %currencyname%&7.");
        pt_br.put("menu.gender.pokemon.name", "&d%pokemonname%");
        pt_br.put("menu.gender.pokemon.lore", "&7Novo Gênero: &d%pokemongender%\n&7Preço: &d&c$%price% %currencyname%");

        //Menu de Desbloqueio de Habilidade Oculta
        pt_br.put("menu.hiddenability.title", "Desbloquear habilidade oculta");
        pt_br.put("menu.hiddenability.cancel.name", "&cCancelar");
        pt_br.put("menu.hiddenability.cancel.lore", "&7Voltar ao menu de edição.");
        pt_br.put("menu.hiddenability.confirm.name", "&aConfirmar");
        pt_br.put("menu.hiddenability.confirm.lore", "&7Isso vai lhe custar &c$%price% %currencyname%&7.");
        pt_br.put("menu.hiddenability.pokemon.name", "&d%pokemonname%");
        pt_br.put("menu.hiddenability.pokemon.lore", "&7Nova Habilidade: &d%pokemonability%\n&7Preço: &d&c$%price% %currencyname%");

        //Menu de Edição de Pokébola
        pt_br.put("menu.growth.title", "Escolha um novo tamanho");
        pt_br.put("menu.growth.lore", "&7Clique para escolher este &aTamanho&7.\n&7Preço: &a$%price% %currencyname%");
        pt_br.put("menu.growth.your", "&7Este já é o &aTamanho &7atual.");

        //Menu de Edição de Nature
        pt_br.put("menu.nature.title", "Escolha uma nova natureza");
        pt_br.put("menu.nature.lore", "&a+ %plusstats%\n&c- %minusstats%\n&7Clique para escolher esta &cNatureza&7.\n&7Preço: &a$%price% %currencyname%");
        pt_br.put("menu.nature.neutral", "&8+- Neutro\n&7Clique para escolher esta &cNatureza&7.\n&7Preço: &a$%price% %currencyname%");
        pt_br.put("menu.nature.your", "&7Esta já é a &cNatureza &7atual.");
        pt_br.put("menu.nature.attack.plus", "&aAumento de Ataque");
        pt_br.put("menu.nature.defence.plus", "&aAumento de Defesa");
        pt_br.put("menu.nature.spattack.plus", "&aAumento de Ataque Especial");
        pt_br.put("menu.nature.spdefence.plus", "&aAumento de Defesa Especial");
        pt_br.put("menu.nature.speed.plus", "&aAumento de Velocidade");
        pt_br.put("menu.nature.attack.minus", "&cRedução de Ataque");
        pt_br.put("menu.nature.defence.minus", "&cRedução de Defesa");
        pt_br.put("menu.nature.spattack.minus", "&cRedução de Ataque Especial");
        pt_br.put("menu.nature.spdefence.minus", "&cRedução de Defesa Especial");
        pt_br.put("menu.nature.speed.minus", "&cRedução de Velocidade");

        //Menu de Edição de IVs
        pt_br.put("menu.ivs.title", "Defina seus IVs");
        pt_br.put("menu.ivs.hp.plus", "&aAumentar HP");
        pt_br.put("menu.ivs.attack.plus", "&aAumentar Ataque");
        pt_br.put("menu.ivs.defence.plus", "&aAumentar Defesa");
        pt_br.put("menu.ivs.specialattack.plus", "&aAumentar Ataque Especial");
        pt_br.put("menu.ivs.specialdefence.plus", "&aAumentar Defesa Especial");
        pt_br.put("menu.ivs.speed.plus", "&aAumentar Velocidade");
        pt_br.put("menu.ivs.hp.minus", "&cDiminuir HP");
        pt_br.put("menu.ivs.attack.minus", "&cDiminuir Ataque");
        pt_br.put("menu.ivs.defence.minus", "&cDiminuir Defesa");
        pt_br.put("menu.ivs.specialattack.minus", "&cDiminuir Ataque Especial");
        pt_br.put("menu.ivs.specialdefence.minus", "&cDiminuir Defesa Especial");
        pt_br.put("menu.ivs.speed.minus", "&cDiminuir Velocidade");
        pt_br.put("menu.ivs.limit.plus", "&7Limite máximo atingido.");
        pt_br.put("menu.ivs.limit.minus", "&7Limite mínimo atingido.");
        pt_br.put("menu.ivs.lore.plus", "&7Clique para aumentar este &bIV &7em &b1 &7por &a$%price% %currencyname%&7.\n&7Shift + Clique para aumentar este &bIV &7em &b10 &7por &a%priceiv10% %currencyname%&7.");
        pt_br.put("menu.ivs.lore.minus", "&7Clique para diminuir este &bIV &7em &b1 &7por &a$%price% %currencyname%&7.\n&7Shift + Clique para diminuir este &bIV &7em &b10 &7por &a%priceiv10% %currencyname%&7.");
        pt_br.put("menu.ivs.max.name", "&bIVs &dmáximos");
        pt_br.put("menu.ivs.max.lore", "&7Clique para deixar todos os &bIVs &7do\n&7seu &dPokémon &7no máximo por &a%ivmaxprice% %currencyname%&7.");
        pt_br.put("menu.ivs.random.name", "&bIVs &caleatórios");
        pt_br.put("menu.ivs.random.lore", "&7Clique para randomizar todos os &bIVs &7do\n&7seu &dPokémon &7por &a$%price% %currencyname%&7.\n&7(3 tipos ficarão no nível máximo)");

        //Menu de Edição de Pokémon
        pt_br.put("menu.edit.title", "Clique em algo para editar");
        pt_br.put("menu.edit.pokeball.name", "&fPokébola");
        pt_br.put("menu.edit.pokeball.lore", "&7Clique para editar a &dPokébola &7em\n&7que o &dPokémon &7foi capturado.");
        pt_br.put("menu.edit.shiny.name", "&eShiny");
        pt_br.put("menu.edit.shiny.lore", "&7Clique para alternar se o &dPokémon\n&7é &eShiny &7ou não.");
        pt_br.put("menu.edit.growth.name", "&aTamanho");
        pt_br.put("menu.edit.growth.lore", "&7Clique para alterar o &aTamanho\n&7do &dPokémon&7.");
        pt_br.put("menu.edit.hiddenability.name", "&5Habilidade Oculta");
        pt_br.put("menu.edit.hiddenability.lore", "&7Clique para liberar a &5Habilidade&7\n&5Oculta &7do seu &dPokémon&7.");
        pt_br.put("menu.edit.hiddenability.your", "&7Seu &dPokémon &7já possui a\n&7sua &5Habilidade Oculta&7.");
        pt_br.put("menu.edit.hiddenability.none", "&7Esse &dPokémon &7não possui\n&7uma &5Habilidade Oculta&7.");
        pt_br.put("menu.edit.gender.name", "&dGênero");
        pt_br.put("menu.edit.gender.lore", "&7Clique para alternar o &dGênero\n&7do seu &dPokémon&7.");
        pt_br.put("menu.edit.gender.nogender", "&7Este &dPokémon &7só possui\n&7um único &dGênero&7.");
        pt_br.put("menu.edit.nature.name", "&cNatureza");
        pt_br.put("menu.edit.nature.lore", "&7Clique para alterar a &cNatureza\n&7do seu &dPokémon&7.");
        pt_br.put("menu.edit.ivs.name", "&bIVs");
        pt_br.put("menu.edit.ivs.lore", "&7Clique para alterar os &bIVs\n&7do seu &dPokémon&7.");

        //Menu de Edição de Pokébola
        pt_br.put("menu.pokeball.title", "Escolha uma nova pokébola");
        pt_br.put("menu.pokeball.lore", "&7Clique para escolher essa &dPokébola&7.\n&7Preço: &a$%price% %currencyname%");
        pt_br.put("menu.pokeball.your", "&7Essa já é a sua &dPokébola&7.");

        //Mensagens no Chat
        pt_br.put("chat.prefix", "&7[&dSeal Builder&7] ");
        pt_br.put("chat.nopokemon", "&7Não foi possível encontrar nenhum &dPokémon &7com o nome informado.");
        pt_br.put("chat.money.insufficient", "&7Você não possui %currencyname% suficientes para fazer isso.");
        pt_br.put("chat.buy.success", "&7Você adquiriu o &dPokémon &7com sucesso.");
        pt_br.put("chat.create", "&7Digite o nome do &dPokémon &7que você deseja.");
        pt_br.put("chat.edit.pokeball", "&7Você alterou a &dPokébola &7do seu &dPokémon &7com sucesso.");
        pt_br.put("chat.create.blacklist", "&7Desculpe, não é permitido a criação deste tipo de &dPokémon&7.");
        pt_br.put("chat.shiny.blacklist", "&7Desculpe, não é permitido a edição do &eShiny &7deste tipo de &dPokémon&7.");
        pt_br.put("chat.growth.blacklist", "&7Desculpe, não é permitido a edição do &aTamanho &7deste tipo de &dPokémon&7.");
        pt_br.put("chat.pokeball.blacklist", "&7Desculpe, não é permitido a edição da &dPokébola &7deste tipo de &dPokémon&7.");
        pt_br.put("chat.edit.shiny", "&7Você alterou o &eShiny &7do seu &dPokémon &7com sucesso.");
        pt_br.put("chat.edit.growth", "&7Você alterou o &aTamanho &7do seu &dPokémon &7com sucesso.");
        pt_br.put("chat.hiddenability.blacklist", "&7Desculpe, não é permitido o desbloqueio da &5Habilidade Oculta &7deste tipo de &dPokémon&7.");
        pt_br.put("chat.edit.hiddenability", "&7Você desbloqueou a &5Habilidade Oculta &7do seu &dPokémon &7com sucesso.");
        pt_br.put("chat.edit.gender", "&7Você alterou o &dGênero &7do seu &dPokémon &7com sucesso.");
        pt_br.put("chat.gender.blacklist", "&7Desculpe, não é permitido a troca de &dGênero &7deste tipo de &dPokémon&7.");
        pt_br.put("chat.edit.nature", "&7Você alterou a &cNatureza &7do seu &dPokémon &7com sucesso.");
        pt_br.put("chat.nature.blacklist", "&7Desculpe, não é permitido a troca de &cNatureza &7deste tipo de &dPokémon&7.");
        pt_br.put("chat.modifier.disabled", "&7Desculpe, esse modificador foi desativado no servidor.");
        pt_br.put("chat.ivs.limit.max", "&7A quantia de pontos desejada ultrapassa o limite máximo.");
        pt_br.put("chat.ivs.limit.min", "&7A quantia de pontos desejada ultrapassa o limite mínimo.");
        pt_br.put("chat.ivs.plus", "&7O &bIV &7do seu &dPokémon &7foi aumentado.");
        pt_br.put("chat.ivs.minus", "&7O &bIV &7do seu &dPokémon &7foi diminuido.");
        pt_br.put("chat.ivs.blacklist", "&7Desculpe, não é permitido a alteração dos &bIVs &7deste tipo de &dPokémon&7.");
        pt_br.put("chat.ivs.random", "&7Os &bIVs &7do seu &dPokémon &7foram randomizados.");
        pt_br.put("chat.onlyplayer", "&7Somente jogadores podem usar esse comando.");
        pt_br.put("chat.nopermission", "&7Você não tem permissão para fazer isso.");
        pt_br.put("chat.nohidden", "&7Esse pokémon não possui uma &5Habilidade Oculta&7.");

        //Básico
        pt_br.put("yes", "Sim");
        pt_br.put("no", "Não");
        pt_br.put("none", "Nenhum");

        //Gêneros
        pt_br.put("gender.male", "Masculino");
        pt_br.put("gender.female", "Feminino");
        pt_br.put("gender.none", "Sem Gênero");

        //Tamanhos
        pt_br.put("growth.microscopic", "Microscópico");
        pt_br.put("growth.pygmy", "Pigmeu");
        pt_br.put("growth.runt", "Nanico");
        pt_br.put("growth.small", "Pequeno");
        pt_br.put("growth.ordinary", "Comum");
        pt_br.put("growth.huge", "Imenso");
        pt_br.put("growth.giant", "Gigante");
        pt_br.put("growth.enormous", "Enorme");
        pt_br.put("growth.ginormous", "Gigantesco");

        //Status
        pt_br.put("statstype.none", "Nenhum");
        pt_br.put("statstype.hp", "HP");
        pt_br.put("statstype.attack", "Ataque");
        pt_br.put("statstype.defence", "Defesa");
        pt_br.put("statstype.specialattack", "Ataque Especial");
        pt_br.put("statstype.specialdefence", "Defesa Especial");
        pt_br.put("statstype.speed", "Velocidade");
        pt_br.put("statstype.accuracy", "Precisão");
        pt_br.put("statstype.evasion", "Evasão");

        //Status "Compactados"
        pt_br.put("statstype.none.compact", "Nenhum");
        pt_br.put("statstype.hp.compact", "HP");
        pt_br.put("statstype.attack.compact", "Ataq.");
        pt_br.put("statstype.defence.compact", "Def.");
        pt_br.put("statstype.specialattack.compact", "Ataq. E.");
        pt_br.put("statstype.specialdefence.compact", "Def. E.");
        pt_br.put("statstype.speed.compact", "Veloc.");
        pt_br.put("statstype.accuracy.compact", "Precis.");
        pt_br.put("statstype.evasion.compact", "Evas.");

        //Nomes de Modifier
        pt_br.put("modifier.growth", "&cTamanho");
        pt_br.put("modifier.shiny", "&cShiny");
        pt_br.put("modifier.pokeball", "&cPokébola");
        pt_br.put("modifier.ivs", "&cIVs");
        pt_br.put("modifier.nature", "&cNature");
        pt_br.put("modifier.gender", "&cGênero");
        pt_br.put("modifier.hiddenability", "&cHabilidade Oculta");

        //Comando
        pt_br.put("command.builder.name", "PokeBuilder");
        pt_br.put("command.builder.description", "Abre o menu do PokeBuilder.");

        translations.put("pt_br", pt_br);

        return translations;
    }

    public static String getLang(String key) {
        return SealBuilder.lang.get(key);
    }

}
