# Made by: @Legopitstop
# Desc: Find the pose using the ID
#
# Called By: lpsm_asp:armor_stand/update

# Find
execute unless data storage lpsm_asp:poses find run data modify storage lpsm_asp:poses find set from entity @s data.id
execute unless data storage lpsm_asp:poses find run return 0

# Loop through all poses
execute store result score #len lpsm_asp.util run data get storage lpsm_asp:poses poses
execute if score #len lpsm_asp.util matches 1.. run function lpsm_asp:armor_stand/find_id_loop

execute unless data storage lpsm_asp:poses pose run tellraw @a {"translate": "Could not find pose \"%s\"", "color": "red", "with": [{"storage": "lpsm_asp:poses", "nbt": "find"}]}
execute unless data storage lpsm_asp:poses pose run return fail
