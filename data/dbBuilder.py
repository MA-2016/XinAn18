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
from py2neo import Graph, Node, Relationship

# open data file
with open('ComprehensiveCAPECDictionary.xml', 'rb') as f:
    data = xmltodict.parse(f.read().decode('utf8'))
    root = data['Attack_Pattern_Catalog']

graph = Graph('http://localhost:7474', username='neo4j', password='root')

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
    relatedAps = {}
    for ap in root['Attack_Patterns']['Attack_Pattern']:
        apNode = Node('Event')
        if '@ID' in ap: apNode['eventId'] = int(ap['@ID'])
        if '@Name' in ap: apNode['name'] = ap['@Name']
        if '@Abstraction' in ap: apNode['eventType'] = ap['@Abstraction']
        # if '@Status' in ap: apNode['status'] = ap['@Status']
        # if '@Description' in ap: apNode['description'] = ap['@Description']
        # if 'Likelihood_Of_Attack' in ap: apNode['likelihoodOfAttack'] = ap['Likelihood_Of_Attack']
        # if 'Typical_Severity' in ap: apNode['typicalSeverity'] = ap['Typical_Severity']
        # if 'Prerequisites' in ap: apNode['prerequisites'] = json.dumps(adapt_list(ap['Prerequisites']['Prerequisite']))
        # if 'Skills_Required' in ap: apNode['skillsRequired'] = json.dumps(sub_SkillsRequired(ap['Skills_Required']['Skill']))
        # if 'Consequences' in ap: apNode['consequences'] = json.dumps(sub_Consequences(ap['Consequences']['Consequence']))
        # if 'Resources_Required' in ap: apNode['resourcesRequired'] = json.dumps(adapt_list(ap['Resources_Required']['Resource']))
        graph.create(apNode)

        # save Related_Attack_Patterns
        if 'Related_Attack_Patterns' in ap:
            relatedAps[apNode['eventId']] = [{
                'eventId': int(rap['@CAPEC_ID'])
            } for rap in adapt_list(ap['Related_Attack_Patterns']['Related_Attack_Pattern']) if rap['@Nature'] == 'ChildOf']
        # PeerOf, CanFollow, CanPrecede

    for eventId, raps in relatedAps.items():
        n1 = graph.nodes.match('Event', eventId=eventId).first()
        for rap in raps:
            n2 = graph.nodes.match('Event', eventId=rap['eventId']).first()
            graph.create(Relationship(n1, 'Derivation', n2))

def handle_Categories():
    rs = {}
    for c in root['Categories']['Category']:
        cNode = Node('Scene')
        if '@ID' in c: cNode['eventId'] = int(c['@ID'])
        if '@Name' in c: cNode['name'] = c['@Name']
        cNode['beScene'] = True
        graph.create(cNode)

        # handle relationships
        if 'Relationships' in c:
            rs[cNode['eventId']] = [int(r['@CAPEC_ID']) for r in adapt_list(c['Relationships']['Has_Member'])]
    
    for eventId, r in rs.items():
        parent = graph.nodes.match(eventId=eventId).first()
        for i in r:
            child = graph.nodes.match(eventId=i).first()
            graph.create(Relationship(child, 'Derivation', parent))

if __name__ == "__main__":
    # handle_AttackPatterns()
    handle_Categories()
