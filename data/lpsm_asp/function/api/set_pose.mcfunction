# Made by: @Legopitstop
# Desc: Set the pose
#
# Called By: Public

execute if entity @s[type=!armor_stand] run tellraw @a {"translate": "Target must be minecraft:armor_stand", "color": "red"}
execute if entity @s[type=!armor_stand] run return fail

execute unless data storage lpsm_asp:poses pose run tellraw @a {"translate": "Missing required value \"pose\" in \"lpsm_asp:poses\"", "color": "red"}
execute unless data storage lpsm_asp:poses pose run return fail

# Check if pose is not already applied. 
scoreboard players set #bool lpsm_asp.util 0
data modify storage lpsm_asp:poses temp.id set from storage lpsm_asp:poses pose.id
execute store success score #bool lpsm_asp.util run data modify storage lpsm_asp:poses temp.id set from entity @n[type=marker, tag=ArmorStandData, limit=1] data.pose
execute if score #bool lpsm_asp.util matches 1 run function lpsm_asp:armor_stand/apply_pose
