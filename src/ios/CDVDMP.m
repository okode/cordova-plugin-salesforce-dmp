#import <Foundation/Foundation.h>
#import "CDVDMP.h"
#import "KruxConsentCallbackImpl.h"

@implementation CDVDMP

static KruxTracker *kt;

+ (KruxTracker *)getKruxTracker {
    return kt;
}

- (void)initialize:(CDVInvokedUrlCommand*)command
{
    NSDictionary* options = [command argumentAtIndex:0];
    NSString* apikey = [options objectForKey:@"apiKey"];
    KruxConsentCallbackImpl *consentCallback = [[KruxConsentCallbackImpl alloc] init];
    kt = [KruxTracker sharedEventTrackerWithConfigId:apikey debugFlag:true dryRunFlag:false consentCallback:consentCallback];
    
    CDVPluginResult* result = [CDVPluginResult
                               resultWithStatus:CDVCommandStatus_OK
                               messageAsString:@"Init Krux OK"];
    
    [self.commandDelegate sendPluginResult:result callbackId:command.callbackId];
}

- (void)sendRequests:(CDVInvokedUrlCommand*)command
{
    NSDictionary* options = [command argumentAtIndex:0];
    NSString* pr = [options objectForKey:@"policyRegime"];
    NSDictionary *consentAttr = [options objectForKey:@"consent"];
    NSMutableDictionary *consentSetAttributes = [consentAttr mutableCopy];
    NSDictionary *idAttributes = [options objectForKey:@"identity"];
    NSMutableDictionary *consentGetAttributes = [idAttributes mutableCopy];
    [self addPolicyRegimeParameter:consentGetAttributes : pr];
    [self addPolicyRegimeParameter:consentSetAttributes : pr];
    
    [kt consentGetRequest:consentGetAttributes];
    [kt consentSetRequest:consentSetAttributes];
    [kt consumerRemoveRequest:idAttributes];
    [kt consumerPortabilityRequest:idAttributes];
    
    CDVPluginResult* result = [CDVPluginResult
                               resultWithStatus:CDVCommandStatus_OK
                               messageAsString:@"Send Krux Requests OK"];
    
    [self.commandDelegate sendPluginResult:result callbackId:command.callbackId];
}

- (void)getSegments:(CDVInvokedUrlCommand*)command
{
    NSArray* segments = [kt getSegments];
    
    CDVPluginResult* result = [CDVPluginResult
                               resultWithStatus:CDVCommandStatus_OK
                               messageAsArray:segments];
    
    [self.commandDelegate sendPluginResult:result callbackId:command.callbackId];
}

- (void)trackPage:(CDVInvokedUrlCommand *)command
{
    NSDictionary* attrs = [command argumentAtIndex:0];
    NSMutableDictionary *pageAttr = [self getPageParameters : attrs];
    NSMutableDictionary *userAttr = [self getUserParameters : attrs];
    [kt trackPageView:@"Home_Page" pageAttributes:pageAttr userAttributes:userAttr];
    
    CDVPluginResult* result = [CDVPluginResult
                               resultWithStatus:CDVCommandStatus_OK
                               messageAsString:@"Track Page Krux OK"];
    
    [self.commandDelegate sendPluginResult:result callbackId:command.callbackId];
}

- (void)fireEvent:(CDVInvokedUrlCommand *)command
{
    NSDictionary *eventAttr = [command argumentAtIndex:0];
    NSError *err;
    [kt fireEvent:[eventAttr objectForKey:@"id"] eventAttributes:eventAttr withError:&err];
    
    CDVPluginResult* result = [CDVPluginResult
                               resultWithStatus:CDVCommandStatus_OK
                               messageAsString:@"Fire Event Krux OK"];
    
    [self.commandDelegate sendPluginResult:result callbackId:command.callbackId];
}

- (void) addPolicyRegimeParameter: (NSMutableDictionary *) attributes : (NSString *) pr
{
    [attributes setValue:pr forKey:@"pr"];
}

- (NSMutableDictionary *) getPageParameters: (NSDictionary *) attributes
{
    NSMutableDictionary *pageAttributes = [[NSMutableDictionary alloc] init];
    [pageAttributes setValue:[attributes objectForKey:@"path"] forKey:@"path"];
    [pageAttributes setValue:[attributes objectForKey:@"type"] forKey:@"type"];
    
    return pageAttributes;
}

- (NSMutableDictionary *) getUserParameters: (NSDictionary *) attributes
{
    NSMutableDictionary *userAttributes = [[NSMutableDictionary alloc] init];
    [userAttributes setValue:[attributes objectForKey:@"email"] forKey:@"email"];
    [userAttributes setValue:[attributes objectForKey:@"logged"] forKey:@"logged"];
    
    return userAttributes;
}

@end
