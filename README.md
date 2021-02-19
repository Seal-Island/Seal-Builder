# Seal Builder
O Seal Builder é um plugin de PokeBuilder para Pixelmon Reforged utilizando a API do Sponge/Bukkit.<br>
Ele permite a compra e edição de pokémons por meio de um menu. Sendo possível a modificação de aspectos como a natureza do pokémon, se é shiny ou não, o tamanho, os IVs, desbloqueio da habilidade oculta, troca de gênero e modificação de pokébola.

Requisitos:
  - [Seal Library](https://github.com/Seal-Island/Seal-Library/releases)
  - Plugins de permissões e economia compatível com a Seal Library. Veja os plugins compatíveis [aqui](https://github.com/Seal-Island/Seal-Library#seal-library).

# Imagens
Menu de seleção de pokémons. Permite a escolha do pokémon que você deseja editar, ou então, caso você possua um slot vazio no seu time, permite a compra de um novo pokémon.<br>
<img src="https://i.imgur.com/biOFgIh.png">
<br><br>
Menu de edição de pokémon. Após selecionar um pokémon, você pode escolher o que deseja alterar nele.
<img src="https://i.imgur.com/qGpw3Zx.png">

# Configuração
Para editar a configuração do Seal Builder é só ir até a pasta de configurações do plugin (*/plugins/SealBuilder/* no Bukkit | */config/SealBuilder/* no Sponge) e abrir com um editor de texto os arquivos *SealBuilder.json* e *SealBuilder-Overrides.json*.

No primeiro arquivo você será capaz de editar as coisas padrões como preços pré-definidos dos módulos, moeda usada e idioma. (Você pode alterar qualquer mensagem do plugin por meio da modificação do arquivo disponível dentro da pasta */SealBuilder/lang/*).

No segundo arquivo é possível sobreescrever preços para pokémons específicos ou então bloquear o uso de algum modificador.
Toda a informação necessária está escrita no próprio arquivo, porém, para referência, aqui estão alguns valores que podem ser úteis na hora de configurar os overrides.

```json5
//Valores-Nomes das Pokébolas:
"poke_ball" - "great_ball" - "ultra_ball" - "master_ball" - "level_ball" - "moon_ball"
"friend_ball" - "love_ball" - "safari_ball" - "heavy_ball" - "fast_ball" - "repeat_ball"
"timer_ball" - "nest_ball" - "net_ball" - "dive_ball" - "luxury_ball" - "heal_ball"
"dusk_ball" - "premier_ball" - "sport_ball" - "quick_ball" - "park_ball" - "lure_ball"
"cherish_ball" - "gs_ball" - "beast_ball" - "dream_ball"

//Valores-Nomes dos Tamanhos
"Microscopic" - "Pygmy" - "Runt" - "Small" - "Ordinary" - "Huge" - "Giant" - "Enormous" - "Ginormous"

//Valores-Nomes das Naturezas
"Hardy" - "Serious" - "Docile" - "Bashful" - "Quirky" - "Lonely"
"Brave" - "Adamant" - "Naughty" - "Bold" - "Relaxed" - "Impish"
"Lax" - "Timid" - "Hasty" - "Jolly" - "Naive" - "Modest"
"Mild" - "Quiet" - "Rash" - "Calm" - "Gentle" - "Sassy" - "Careful"

//Valores-Nomes dos IVs-Status
"HP" - "Attack" - "Defence" - "SpecialAttack"
"SpecialDefence" - "Speed" - "Accuracy" - "Evasion"

//Valores-Nomes dos Módulos para Blacklist
"create" - "shiny" - "hiddenability" - "gender"
"nature" - "pokeball" - "growth" - "ivs"
```