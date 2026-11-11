# Made by: @lpsmods
# Desc: Update poses 1 -> 2
#
# Called By: #lpsm_asp:datafixer

# Check if format_version < 2
execute store result score #version lpsm_asp.util run data get storage lpsm_asp:poses format_version
execute if score #version lpsm_asp.util matches 2.. run return 1

data modify storage lpsm_asp:poses poses[{id: "default",}].display_name set value {"type": "text", "text": "Default"}
data modify storage lpsm_asp:poses poses[{id: "none",}].display_name set value {"type": "text", "text": "None"}
data modify storage lpsm_asp:poses poses[{id: "solemn",}].display_name set value {"type": "text", "text": "Solemn"}
data modify storage lpsm_asp:poses poses[{id: "athena",}].display_name set value {"type": "text", "text": "Athena"}
data modify storage lpsm_asp:poses poses[{id: "brandish",}].display_name set value {"type": "text", "text": "Brandish"}
data modify storage lpsm_asp:poses poses[{id: "honor",}].display_name set value {"type": "text", "text": "Honor"}
data modify storage lpsm_asp:poses poses[{id: "entertain", }].display_name set value {"type": "text", "text": "Entertain"}
data modify storage lpsm_asp:poses poses[{id: "salute",}].display_name set value {"type": "text", "text": "Salute"}
data modify storage lpsm_asp:poses poses[{id: "riposte",}].display_name set value {"type": "text", "text": "Riposte"}
data modify storage lpsm_asp:poses poses[{id: "zombie",}].display_name set value {"type": "text", "text": "Zombie"}
data modify storage lpsm_asp:poses poses[{id: "can_can_a",}].display_name set value {"type": "text", "text": "Can Can A"}
data modify storage lpsm_asp:poses poses[{id: "can_can_b",}].display_name set value {"type": "text", "text": "Can Can B"}
data modify storage lpsm_asp:poses poses[{id: "hero",}].display_name set value {"type": "text", "text": "Hero"}

data modify storage lpsm_asp:poses format_version set value 2
