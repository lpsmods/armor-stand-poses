# Made by: @Legopitstop
# Desc: hero pose
#
# Called By: lpsm_asp:redstone && lpsm_asp:comparator

# reset
function lpsm_asp:armor_stand/reset

data modify entity @s Pose set from storage lpsm_asp:poses poses[{id: "hero"}].Pose
tag @s add pose_13
