# Made by: @Legopitstop
# Desc: Reloads all poses
#
# Called By: Player

# Message
tellraw @a {"type": "text", "text": "Reloading poses!"}

data modify storage lpsm_asp:poses poses set value []
function #lpsm_asp:register_poses

# Count
execute store result score #Poses lpsm_asp.util run data get storage lpsm_asp:poses poses

# Count Message
tellraw @a {"translate": "Loaded %s armor stand poses", "with": [{"score": {"name": "#Poses", "objective": "lpsm_asp.util"}}]}
