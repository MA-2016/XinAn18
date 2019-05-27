# !/usr/bin/python3

# abondoned fields:
#   Attack_Patterns/AttackPattern/Mitigations
#   Attack_Patterns/AttackPattern/Example_Instance
#   Attack_Patterns/AttackPattern/Content_History
#   Attack_Patterns/AttackPattern/Taxonomy_Mapping
#   Attack_Patterns/AttackPattern/Prerequisites
#   Attack_Patterns/AttackPattern/Skills_Required
#   Attack_Patterns/AttackPattern/Consequences
#   Attack_Patterns/AttackPattern/Resources_Required
#   Attack_Patterns/Execution_Flow/Attack_Step/Technique
#
# questions:
#   what's taxonomy mapping (分类匹配) for?
# note:


import xmltodict
import collections
import json
from random import randint
import datetime

# open data file
with open('ComprehensiveCAPECDictionary.xml', 'rb') as f:
    data = xmltodict.parse(f.read().decode('utf8'))
    root = data['Attack_Pattern_Catalog']

# help function

def is_list(obj):
    return isinstance(obj, list)

def adapt_list(item):
    return [i for i in item if not i is None] if is_list(item) else [item]

def sub_SkillsRequired(item):
    ret = []
    for i in adapt_list(item):
        obj = {}
        if '@Level' in i: obj['level'] = i['@Level']
        if '#text' in i: obj['skill'] = i['#text']
        ret.append(obj)
    return ret

def sub_Consequences(item):
    return [{'scopes': adapt_list(i['Scope']), 'impact': i['Impact']} for i in adapt_list(item)]

# Scene(name -> @Name, eventId -> @ID, beScene -> True)
# Event(name -> @Name, eventId -> @ID, eventType -> @Absctraction)
# Derivation = child_node -> parent_node

def handle_AttackPatterns():
    for ap in root['Attack_Patterns']['Attack_Pattern']:
        apNode = dict(type='Event')
        if '@ID' in ap: apNode['eventId'] = int(ap['@ID'])
        if '@Name' in ap: apNode['name'] = ap['@Name']
        if '@Abstraction' in ap: apNode['eventType'] = ap['@Abstraction']
        nodes.append(apNode)

def handle_Categories():
    for c in root['Categories']['Category']:
        cNode = dict(type='Scene')
        if '@ID' in c: cNode['eventId'] = int(c['@ID'])
        if '@Name' in c: cNode['name'] = c['@Name']
        cNode['beScene'] = True
        nodes.append(cNode)

nodes = []
t = datetime.datetime.now()
if __name__ == "__main__":
    handle_AttackPatterns()
    handle_Categories()
    for i in range(50):
        n = nodes[randint(0, len(nodes))]
        t = t + datetime.timedelta(seconds=randint(20, 120))
        if 'eventType' in n:
            s = 'name: %s, eventId: %d, eventType: %s' \
                % (n['name'], n['eventId'], n['eventType'])
        else:
            s = 'name: %s, eventId: %d, beScene: %s' \
                % (n['name'], n['eventId'], n['beScene'])
        print('[INFO]', t.ctime(),
            'source: 192.168.1.113, target: 192.168.1.150, ' + s)
